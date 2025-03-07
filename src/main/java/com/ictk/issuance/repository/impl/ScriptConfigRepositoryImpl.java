package com.ictk.issuance.repository.impl;

import com.ictk.issuance.data.model.ScriptConfig;
import com.ictk.issuance.repository.dao.ScriptConfigDao;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ictk.issuance.data.model.QScriptConfig.scriptConfig;


@Slf4j
@RequiredArgsConstructor
public class ScriptConfigRepositoryImpl extends IssuanceDaoImpl implements ScriptConfigDao {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean makeTable(String database, String tableName) {
        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append("CREATE TABLE IF NOT EXISTS `"+tableName+"` ( \n");
        sbSQL.append("  `seq` int(11) NOT NULL COMMENT '순번 1부터 시작', \n");
        sbSQL.append("  `scrt_id` varchar(32) NOT NULL COMMENT '스크립트 ID. scrt_ + seq 의 형식', \n");
        sbSQL.append("  `scrt_name` varchar(256) NOT NULL COMMENT '스크립트 이름', \n");
        sbSQL.append("  `description` varchar(256) DEFAULT NULL COMMENT '스크립트 상세 설명', \n");
        sbSQL.append("  `scrt_type` varchar(32) DEFAULT NULL COMMENT '스크립트 타입(필요시)', \n");
        sbSQL.append("  `version` varchar(32) DEFAULT NULL COMMENT '버전 ex: 2.05', \n");
        sbSQL.append("  `ctnt_data` text DEFAULT NULL COMMENT '프로파일 컨텐츠 데이터', \n");
        sbSQL.append("  `data_hash` varchar(64) DEFAULT NULL COMMENT '프로파일 데이터 해시', \n");
        sbSQL.append("  `updated_at` datetime NOT NULL COMMENT '업데이트 시간', \n");
        sbSQL.append("  `created_at` datetime NOT NULL COMMENT '등록 시간', \n");
        sbSQL.append("  `comment` text DEFAULT NULL COMMENT '주석 기타정보', \n");
        sbSQL.append("  PRIMARY KEY (`scrt_id`), \n");
        sbSQL.append("  UNIQUE KEY `IDX_SCRIPT_CONFIG_UK` (`seq`) \n");
        sbSQL.append("  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci; \n");

        log.info(sbSQL.toString());
        entityManager.createNativeQuery(sbSQL.toString()).executeUpdate();

        return isTableExist(database, tableName);
    }

    // 스크립트 설정 조회 (페이징)
    @Override
    public Tuple2<Long, Page<ScriptConfig>> getScriptConfigPageByCondition(
            Predicate queryConds,
            Pageable pageable,
            List<OrderSpecifier> orderSpecifiers) {

        // total count 구하기
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(scriptConfig.count())
                .from(scriptConfig)
                .where(queryConds);

        Long total = countQuery.fetchCount();

        return Tuple.of(
                total,
                new PageImpl<>(
                        jpaQueryFactory.selectFrom(scriptConfig)
                                .where(queryConds)
                                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]))
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .fetch(),
                        pageable,
                        total ));

    }

    // 스크립트 설정 삭제
    @Override
    @Transactional
    public long deleteScriptConfigByScrtId(String scrtId) {
        return jpaQueryFactory
                .delete(scriptConfig)
                .where(scriptConfig.scrtId.eq(scrtId))
                .execute();
    }

    @Override
    public List<String> findAllScrtIds() {
        return jpaQueryFactory
                .select(scriptConfig.scrtId)
                .from(scriptConfig)
                .fetch();
    }
}
