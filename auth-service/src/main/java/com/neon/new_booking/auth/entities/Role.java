package com.neon.new_booking.auth.entities;


public enum Role {
    ROLE_GUEST, //пользователь, арендатор
    ROLE_OWNER, //владелец сервиса, ему нужна статистика, отчеты, прибыль
    ROLE_LEGAL_HOST, //арендодатель, владелец жилья, объекта (юр лицо), не может арендовывать
    ROLE_INDIVIDUAL_HOST, //арендодатель, владелец жилья, объекта (физ лицо), может арендовывать
    ROLE_ADMIN; //администратор сайта, поддержка

    public static boolean isUserHost(String role){
        return Role.valueOf(role).equals(Role.ROLE_LEGAL_HOST) || Role.valueOf(role).equals(Role.ROLE_INDIVIDUAL_HOST);
    }

    public static boolean isUserGuest(String role){
        return Role.valueOf(role).equals(Role.ROLE_GUEST);
    }
}
