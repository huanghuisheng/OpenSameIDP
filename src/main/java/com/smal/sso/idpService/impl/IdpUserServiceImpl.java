package com.smal.sso.idpService.impl;

import com.smal.sso.idpDao.IdpUserDAO;
import com.smal.sso.idpDto.ArtifactDTO;
import com.smal.sso.idpDto.ObjectRsDTO;
import com.smal.sso.idpPojo.DsServiceProvider;
import com.smal.sso.idpPojo.DsUser;
import com.smal.sso.idpService.IdpUserService;
import com.smal.sso.idpWebAction.IdpSsoController;
import com.smal.sso.util.IDPConstants;
import com.smal.sso.util.generator.CustomGenerator;
import com.tone.waf.dao.DaoFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class IdpUserServiceImpl implements IdpUserService {
    private static Logger logger = (Logger) LoggerFactory.getLogger(IdpSsoController.class);
    private static Map<String, ArtifactDTO> artifactList = new HashMap<String, ArtifactDTO>();
    private IdpUserDAO idpUserDAO = (IdpUserDAO) DaoFactory.getDao(IdpUserDAO.class);



    public int getUser(DsUser user, HttpServletRequest req) {
        if (user.getLoginName() == null) {
            return -1;
        }
        //判断是否有此用户
        List<DsUser> list = idpUserDAO.getUser(user);
        if (list != null && list.size() > 0) {
            for (DsUser user1 : list) {
                if (user1.getPassword().equals(user.getPassword())) {
                    req.getSession().setAttribute(IDPConstants.SESSION_USER_OBJ, user1);
                    return 1;
                }
            }
            return -2;
        } else {
            return -1;
        }
    }





    public ObjectRsDTO  saveServiceProvider(DsServiceProvider serviceProvider) {
        ObjectRsDTO dto =new ObjectRsDTO();
        List<DsServiceProvider> list = idpUserDAO.queryServiceProvider(serviceProvider);
        if (list != null && list.size() == 0) {
            serviceProvider.setId((Long) CustomGenerator.generate());
            idpUserDAO.saveServiceProvider(serviceProvider);
        }
        dto.setCode(1);
        dto.setMessage("登记成功");
        return dto;
    }

    public ObjectRsDTO  saveBatchUser(CommonsMultipartFile file) {
        ObjectRsDTO dto =new ObjectRsDTO ();
        dto.setCode(1);
        InputStream input = null;
        Workbook wb = null;
        try {
            input = file.getInputStream();
            wb = new XSSFWorkbook(input);
        } catch (IOException e) {
            e.printStackTrace();
            dto.setCode(-1);
            dto.setMessage("解析文件错误");
            return dto;
        }

        // 根据文件格式(2003或者2007)来初始化
        Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
        Iterator<Row> rows = sheet.rowIterator();
        List<DsUser> listUser = new ArrayList<DsUser>();
        List errorImei = new ArrayList<Object>();

        while (rows.hasNext()) {
            Row row = rows.next(); // 获得行数据
            Iterator<Cell> cells = row.cellIterator(); // 获得第一行的迭代器
            if (row.getRowNum() == 0) {
                continue;
            }
//            用户名称	地区	性别	电话号码	email地址	密码	登记系统编码
//            while (cells.hasNext()) {
//                Cell cell = cells.next();
//            }
            Long region =row.getCell(1)==null||("").equals(row.getCell(1).getStringCellValue())?null:Long.valueOf(row.getCell(1).getStringCellValue());
            Long sex =row.getCell(2)==null||("").equals(row.getCell(2).getStringCellValue())?null:Long.valueOf(row.getCell(2).getStringCellValue());
            String phone =row.getCell(3)==null||("").equals(row.getCell(3).getStringCellValue())?null:row.getCell(3).getStringCellValue();
            String email =row.getCell(4)==null||("").equals(row.getCell(4).getStringCellValue())?null:row.getCell(4).getStringCellValue();
            String password =row.getCell(5)==null||("").equals(row.getCell(5).getStringCellValue())?null:row.getCell(4).getStringCellValue();

            DsUser user = new DsUser();
            user.setId((Long)CustomGenerator.generate());
            user.setLoginName(row.getCell(0).getStringCellValue());
            user.setRegion(region);
            user.setSex(sex);
            user.setPhone(phone);
            user.setEmail(email);
            user.setPassword(password);
//            DsUser object = idpSsoDAO.queryUserById(user);
//            if (object == null) {
                listUser.add(user);
//            }
        }

         //批量保存用户信息
        idpUserDAO.saveUserByBatch(listUser);
         dto.setMessage("保存成功");
         return dto;
    }

    public ObjectRsDTO  saveUser(DsUser user) {
        ObjectRsDTO dto=new ObjectRsDTO();
        List<DsUser> list = idpUserDAO.getUser(user);
        if (list != null && list.size() == 0) {
            idpUserDAO.saveUser(user);
        }
        dto.setCode(1);
        dto.setMessage("保存用户成功");
        return dto;
    }

}