package com.may.apimanagementsystem.service;

import com.github.pagehelper.PageHelper;
import com.may.apimanagementsystem.constant.ExceptionMessage;
import com.may.apimanagementsystem.dao.InterfaceMapper;
import com.may.apimanagementsystem.exception.ParameterException;
import com.may.apimanagementsystem.exception.ResourceNotFoundException;
import com.may.apimanagementsystem.exception.ServerException;
import com.may.apimanagementsystem.po.Interfaces;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.may.apimanagementsystem.constant.ExceptionMessage.*;

@Service
public class InterfaceService {

    @Resource
    private InterfaceMapper interfaceMapper;

    public List<Interfaces> getInterfaces(int projectId) {
        List<Interfaces> interfaceList = interfaceMapper.getInterfaceList(projectId);
        return interfaceList;
    }

    public void addInterface(Interfaces interfaces) {
        if (!StringUtils.isNotBlank(interfaces.getInterfaceName()) || !StringUtils.isNotBlank(interfaces.getJson()) || !StringUtils.isNotBlank(interfaces.getMethod()) || !StringUtils.isNotBlank(interfaces.getUrl()) || interfaces.getProjectId() == 0)
            throw new ParameterException(ExceptionMessage.PARAMETER_CANNOT_NULL);
        checkInterfaceName(interfaces.getInterfaceName());
        if (!interfaceMapper.insertInterface(interfaces)) {
            throw new ServerException();
        }

    }

    public void updateInterface(Interfaces interfaces) {
        if (!StringUtils.isNotBlank(interfaces.getInterfaceName()) || !StringUtils.isNotBlank(interfaces.getJson()) || !StringUtils.isNotBlank(interfaces.getMethod()) || !StringUtils.isNotBlank(interfaces.getUrl()) )
            throw new ParameterException(ExceptionMessage.PARAMETER_CANNOT_NULL);
        checkInterfaceName(interfaces.getInterfaceName());
        if (!interfaceMapper.updateInterface(interfaces)) {
            throw new ServerException();
        }
    }


    public void removeInterface(int interfaceId) {

        if (!interfaceMapper.deleteInterface(interfaceId)) {
            throw new ServerException();
        }

    }

    public Interfaces getInterfaceByInterfaceId(int interfaceId) {
        Interfaces interfaces = interfaceMapper.findInterfaceByInterfaceId(interfaceId);
        return interfaces;
    }


    public Interfaces getInterfacesByInterfaceName(String interfaceName) {
        Interfaces interfaces = interfaceMapper.findInterfaceByInterfaceName(interfaceName);
        return interfaces;
    }


    private void checkInterfaceName(String interfaceName) {

        Interfaces interfaces = getInterfacesByInterfaceName(interfaceName);
        if (interfaces != null) {
            throw new ParameterException(ExceptionMessage.DOUBLE_INTERFACE_NAME);
        }
    }

    public Interfaces getInterface(String url){

        Interfaces interfaces = interfaceMapper.findInterfaceByInterfaceUrl(url);
        return interfaces;
    }


    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;

    }
//    public String downloadInterface(HttpServletRequest request, HttpServletResponse response) {
//        String fileName = "aim_test.txt";// 设置文件名，根据业务需要替换成要下载的文件名
//        if (fileName != null) {
//            //设置文件路径
//            String realPath = "D://api//";
//            File file = new File(realPath, fileName);
//            if (file.exists()) {
//                response.setContentType("application/force-download");// 设置强制下载不打开
//                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
//                byte[] buffer = new byte[1024];
//                FileInputStream fis = null;
//                BufferedInputStream bis = null;
//                try {
//                    fis = new FileInputStream(file);
//                    bis = new BufferedInputStream(fis);
//                    OutputStream os = response.getOutputStream();
//                    int i = bis.read(buffer);
//                    while (i != -1) {
//                        os.write(buffer, 0, i);
//                        i = bis.read(buffer);
//                    }
//                    System.out.println("success");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (bis != null) {
//                        try {
//                            bis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (fis != null) {
//                        try {
//                            fis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }

}
