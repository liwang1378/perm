package com.huatec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysLog is a Querydsl query type for SysLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysLog extends EntityPathBase<SysLog> {

    private static final long serialVersionUID = 2113226015L;

    public static final QSysLog sysLog = new QSysLog("sysLog");

    public final QBaseModel _super = new QBaseModel(this);

    public final StringPath actionName = createString("actionName");

    //inherited
    public final StringPath addBy = _super.addBy;

    //inherited
    public final DateTimePath<java.util.Date> addTime = _super.addTime;

    public final NumberPath<Integer> ajax = createNumber("ajax", Integer.class);

    public final StringPath classMethod = createString("classMethod");

    public final StringPath httpMethod = createString("httpMethod");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath ip = createString("ip");

    public final StringPath params = createString("params");

    public final StringPath response = createString("response");

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DateTimePath<java.util.Date> updateTime = _super.updateTime;

    public final StringPath uri = createString("uri");

    public final StringPath userId = createString("userId");

    public final StringPath username = createString("username");

    public QSysLog(String variable) {
        super(SysLog.class, forVariable(variable));
    }

    public QSysLog(Path<? extends SysLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysLog(PathMetadata metadata) {
        super(SysLog.class, metadata);
    }

}

