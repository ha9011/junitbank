package com.example.junit_bank.temp;


import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

// java.util.regex.Pattern
public class RegexTest {
    
    @Test
    public void 한글만된다_test() throws Exception{
        //given
        String value = "가나";
        boolean result = Pattern.matches("^[ㄱ-힣]+$", value);
        System.out.println("테스트 : " + result);

        //when
        
        //then
    }
    
    @Test
    public void 한글안된다_test() throws Exception{
        //given
        String value = "";
        boolean result = Pattern.matches("^[^ㄱ-힣]+$", value);
        System.out.println("테스트 : " + result);
        
        //when
        
        //then
    }

    @Test
    public void 영어만된다_test() throws Exception{
        //given
        String value = "ssar";
        boolean result = Pattern.matches("^[a-zA-Z]+$", value);
        System.out.println("테스트 : " + result);

        //when

        //then
    }

    @Test
    public void 영어는안된다_test() throws Exception{
        //given
        String value = "ssar";
        boolean result = Pattern.matches("^[^a-zA-Z]*$", value);
        System.out.println("테스트 : " + result);

        //when

        //then
    }
    
    @Test
    public void 영어와숫자만된다() throws Exception{
        //given
        String value = "s3sar123";
        boolean result = Pattern.matches("^[a-zA-Z0-9]+$", value);
        System.out.println("테스트 : " + result);
        
        //when
        
        //then
    }
    
    @Test
    public void 영어만되고_길이는최소2최대4이다_test() throws Exception{
        //given
        String value = "s3sar123";
        boolean result = Pattern.matches("^[a-zA-Z]{2,4}$", value);
        System.out.println("테스트 : " + result);
        
        //when
        
        //then
    }

    @Test
    public void username_test() throws Exception{
        //given
        String username = "ssar";
        boolean result = Pattern.matches("^[0-9a-zA-Z]{2,20}$", username);
        System.out.println("테스트 : " + result);

        //when

        //then
    }

    @Test
    public void userfullname_test() throws Exception{
        //given
        String fullname = "가ㄱ";
        boolean result = Pattern.matches("^[ㄱ-힣a-zA-Z]{1,20}$", fullname);
        System.out.println("테스트 : " + result);

        //when

        //then
    }
    @Test
    public void email_test() throws Exception{
        //given
        String email = "ssar@nate.com";
        boolean result = Pattern.matches("^[0-9a-zA-Z]{2,6}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", email);
        System.out.println("테스트 : " + result);

        //when

        //then
    }
}
