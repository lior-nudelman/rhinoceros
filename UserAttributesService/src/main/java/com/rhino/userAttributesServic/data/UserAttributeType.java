package com.rhino.userAttributesServic.data;

public enum UserAttributeType {
    MAIL(1000,"mail"),
    MAIL_TYPE(1010,"mailType"),
    FIRST_NAME(1020,"firstName"),
    LAST_NAME(1030,"lastName"),
    NAME(1040,"name"),
    FOLDER_PATH(1050,"folderPath"),
    MAIL_USERNAME(2000,"MAIL_USERNAME"),
    MAIL_PASSWORD(2010,"MAIL_PASSWORD"),
    
    ERROR(8000,"ERROR"),
    DEFAULT(9000,"DEFAULT"),
    ;
    private Integer numericCode;
    private String stringCode;

    private UserAttributeType(Integer numericCode, String stringCode){
        this.numericCode = numericCode;
        this.stringCode = stringCode;
    }

    public Integer getNumericCode(){
        return numericCode;
    }

    public String getStringCode(){
        return stringCode;
    }
    
    public static UserAttributeType valueOfStringCode (String stringCode){
    	UserAttributeType resultQtype = null;
	for (UserAttributeType qt : UserAttributeType.values()){
	    if (qt.getStringCode().equals(stringCode)){
		resultQtype = qt;
		break;
	    }
	}
	return resultQtype;
    }
}
