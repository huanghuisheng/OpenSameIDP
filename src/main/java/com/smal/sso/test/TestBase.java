package com.smal.sso.test;

import com.smal.sso.util.generator.CustomGenerator;

public class TestBase {
    public static void main(String[] args) {

//        BaseDAO dao =new BaseDAO();
//        DsServiceProvider user=new DsServiceProvider();
////        user.setId(123456789L);
//        DsServiceProvider user1= (DsServiceProvider) dao.selectOne(user);
//        System.out.println("----"+user1.getSeviceName());
//        int APP_ID_BIT=63-10;
//        System.out.println("-----"+APP_ID_BIT);
//
//        int MILSECOND_BIT=63-10-4-7-4-5-5-6-6-10;
//        System.out.println("-----"+MILSECOND_BIT);

//        int a=9;
//        int b=3;
//        a=a|b;
//        System.out.println("----"+a);


//        String id= UUID.randomUUID().toString();//生成的id942cd30b-16c8-449e-8dc5-028f38495bb5中间含有横杠，<span style="color: rgb(75, 75, 75); font-family: Verdana, Arial, Helvetica, sans-serif; line-height: 20.7999992370605px;">用来生成数据库的主键id是很实用的。</span>
//        id=id.replace("-", "");//替换掉中间的那个斜杠
//
//        System.out.println("----"+id);

        Long id = (Long) CustomGenerator.generate();
        System.out.println("-------" + id);
    }
}
