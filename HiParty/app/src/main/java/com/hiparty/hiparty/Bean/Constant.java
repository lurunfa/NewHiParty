package com.hiparty.hiparty.Bean;

/**
 * Constant     2017-03-21
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class Constant {
    public static final String ADDRESS = "192.168.43.169";
    public static final int PORT = 9999;
    public static final String SUCCEED = "SUCCEED";
    public static final String ROOM_NAME = "roomname";
    public enum Order {
        /**
         * 创建房间
         */
        create(1),

        /**
         * 进入房间
         */
        in(2),
        /**
         * 登录
         */
        login(3),

        /**
         * 注销
         */
        logoff(4),

        /**
         * 退出
         */
        out(5),

        /**
         * 确认自我介绍
         */
        ensure_introduce(6),
        /**
         * 自我介绍
         */
        introduce(7),
        /**
         * 排位
         */
        rank(8),

        talk(9),
        punishment(10),
        ensure_punishment(11)
        ;

        private int index;
        Order(int i) {
            index = i;
        }

        public int getIndex() {
            return index;
        }
    }

    public enum ShitTypeEnum{
        /**
         * 在大厅
         */
        inLobby,

        inRoom,

        inModuel,

        inMy;
    }

    public enum ConnectTypeEnum{
        CONNECT,
        DISCONNECT
    }


}
