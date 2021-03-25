package com.c0920g1.c0920g1carinsurancebe.utils;

public class Regex {
    public static final String NAME_REGEX = "^[\\w\\sa-zA-Z0-9éẻẽẹêếểễệaáảãạăắẳẵặâấầẩẫóòỏõọôốổỗộơớờởỡúùủũụưứừữửựíìỉĩịýỳỷỹỵÉẺẼẸÊẾỂỄỆAÁẢÃẠĂẮẲẴẶÂẤẦẨẪÓÒỎÕỌÔỐỔỖỘƠỚỜỞỠÚÙỦŨỤƯỨỪỮỬỰÍÌỈĨỊÝỲỶỸỴ-]{1,45}$";
    public static final String NUMBER_REGEX ="^[0-9]{1,9}$" ;
    public static final String STATUS_REGEX = "^(true|false)$";
    public static final String NUMBER_PERSON_REGEX =  "^\\d{1,2}$";

}
