package org.ki.cloud.living.logic;

import org.ki.cloud.living.common.web.Result;
import org.ki.cloud.living.common.web.excption.RegisterException;
import org.ki.cloud.living.logic.bean.TActionCode;
import org.ki.cloud.living.logic.dao.ActionCodeDao;
import org.ki.cloud.living.logic.model.RegisterRequestModel;
import org.ki.cloud.living.logic.model.RegisterResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class ActionCodeController {


    @Autowired
    private ActionCodeDao actionCodeDao;


    @RequestMapping("/register/{project}/{deviceSignatureCode}")
    public Mono<Result<RegisterResponseModel>> registerDevice(@PathVariable String deviceSignatureCode, @PathVariable String project) {

        return actionCodeDao.findByDeviceSignatureCode(deviceSignatureCode)
                .defaultIfEmpty(new TActionCode())
                .flatMap(a -> {
                    if (a.getDeviceName()==null){
                        return actionCodeDao.countByDeviceSignatureCode().flatMap(i->{
                            if (i!=0){
                                return actionCodeDao.registerDevice(deviceSignatureCode, project)
                                        .then(Mono.defer(() -> actionCodeDao.findByDeviceSignatureCode(deviceSignatureCode)));
                            }
                            return Mono.empty();
                        }).switchIfEmpty(Mono.defer(()->Mono.just(a)));
                    }else {
                        return actionCodeDao.findByDeviceSignatureCode(deviceSignatureCode);
                    }
                })
                .map(ss -> {
                    Result<RegisterResponseModel> registerResponseModelResult = new Result<>();
                    if (ss.getDeviceSignatureCode()==null){
                        registerResponseModelResult.setCode(400);
                        registerResponseModelResult.setMessage("项目"+project+"的key不足");
                        return registerResponseModelResult;
                    }
                    return registerResponseModelResult.ok(new RegisterResponseModel(ss));
                })
                ;

    }

    @RequestMapping("/register")
    public Mono<Result<RegisterResponseModel>> registerDevice1(RegisterRequestModel registerRequestModel) {

        return actionCodeDao.findByDeviceSignatureCode(registerRequestModel.getDeviceSignatureCode())
                .defaultIfEmpty(new TActionCode())
                .flatMap(a -> actionCodeDao.countByDeviceSignatureCode().flatMap(i->{
                    if (i!=0){
                        return actionCodeDao.registerDevice(registerRequestModel.getDeviceSignatureCode(), registerRequestModel.getProject())
                                .then(Mono.defer(() -> actionCodeDao.findByDeviceSignatureCode(registerRequestModel.getDeviceSignatureCode())));
                    }
                    return Mono.empty();
                }).switchIfEmpty(Mono.defer(()->Mono.just(a))))
                .map(ss -> {
                    Result<RegisterResponseModel> registerResponseModelResult = new Result<>();
                    if (ss.getDeviceSignatureCode()==null){
                        registerResponseModelResult.setCode(400);
                        registerResponseModelResult.setMessage("项目"+registerRequestModel.getProject()+"的key不足");
                        return registerResponseModelResult;
                    }
                    return registerResponseModelResult.ok(new RegisterResponseModel(ss));
                })
                ;

    }

    @GetMapping("/all")
    public Flux<TActionCode> getAll() {
        return actionCodeDao.findAll();
    }
}
