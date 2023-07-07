package com.example.fon_classroommanagment.Configuration;

public class Constants {
    public static  final String SECRET="FON_CLASSROOMS";



    public static  final int VALIDATION_TOKEN_EXPIRATION=100000*60;

    public static  final int EXPIRATION_TIME=60*10*1000;
    public static  final int REFRESH_TOKEN_EXPIRATION=10000*60;

    public static  final String VALIDATION_TOKEN_HEDER_NAME="validationToken";

    public static  final String REFRESH_TOKEN_HEDER_NAME="refreshToken";

    public static  final String USER_PROFITE_TABLE_NAME="user_profile";
    public static  final String USER_ROLE_TABLE_NAME="user_role";
    public static  final String ACCOUNT_TABLE_NAME="user_account";
    public static  final String VALIDATION_TOKEN_ACCOUNT="validation_account_token";

    public static  final String EMPLOYEE_TABLE_NAME="employee";
    public static  final String EDUCATION_TITLE_TABLE_NAME="education_title";
    public static  final String EMPLOYEE_TYPE_TABLE_NAME="employeeType";
    public static  final String EMPLOYEE_DEPARTMENT_TABLE_NAME="employee_department";

    public static  final String CLASSROOM_TABLE_NAME="classroom";
    public static  final String CLASSROOM_TYPE_TABLE_NAME="classroom_type";


    public static  final String APPOINTMENT_TABLE_NAME="appointment";
    public static  final String APPOINTMENT_TYPE_TABLE_NAME="appointment_type";
    public static  final String APPOINTMENT_STATUS_TABLE_NAME="appointment_status";

    public static  final String EMAIL_HOST_SENDER="radojkovicika@gmail.com";

    public static  final String EMAIL_REGISTRATION_REQUEST_TEMPLATE="RegistrationEmail.html";

    public static  final String EMAIL_APPOINTMENT_APPROVED_TEMPLATE="AppointmentApproved.html";

    public static  final int MIN_CAPACITY=1;
    public  static  final  String APPOINTMENT_DECLINED="DECLINED";
    public  static  final  String APPOINTMENT_PENDING="PENDING";
    public  static  final  String APPOINTMENT_APPROVED="APPROVED";
    public static  final int MAX_CAPACITY=120;

    public static  final int PAGE_SIZE=10;
    public static  final String RC_TYPE_NAME="RC";
    //stavi da je 1 kasnije jer je meni u bazi 3,a cekanje treba da bude 1. kao
    public static  final long STATUS_PENDING =3L;
    public static  final long STATUS_APPROVED=1L;
    public static  final long STATUS_DECLINED=2L;


    public static  final int MAX_VREME_ZAKAZIVANJA=20;
    public static  final int MIN_VREME_ZAKAZIVANJA=8;
    public static  final String BEARER_STRING="Bearer " ;

    public static final int CHIP_SEARCH_ELEMENTS=3;

public static final String ADMIN_NAME_TYPE_ROLE="ADMIN";
public static final Long ADMIN_ID_ROLE=1L;
public static final String USER_NAME_TYPE_ROLE="USER";
public static final Long USER_ID_ROLE=2L;



}
