package org.ki.cloud.living.logic.dao;

import org.ki.cloud.living.logic.bean.TActionCode;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface ActionCodeDao extends ReactiveCrudRepository<TActionCode,Long> {


    @Query("UPDATE t_action_code SET device_signature_code = :deviceSignatureCode WHERE device_signature_code IS NULL and project_id = :projectId LIMIT 1")
    public Mono<Void> registerDevice(@Param("deviceSignatureCode") String deviceSignatureCode, @Param("projectId")String projectId);


    @Query("SELECT  * from t_action_code where device_signature_code = :deviceSignatureCode ")
    public Mono<TActionCode> findByDeviceSignatureCode(@Param("deviceSignatureCode") String deviceSignatureCode);

    @Query("SELECT COUNT(*) AS count FROM t_action_code WHERE device_signature_code IS NULL")
    public Mono<Integer> countByDeviceSignatureCode();

}
