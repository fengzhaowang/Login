package top.lovefeifei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.lovefeifei.entity.User;
import top.lovefeifei.service.IndexService;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RequestMapping("/system")
@Controller
public class IndexController {
    @Autowired
    public IndexService indexService;

    final private char[] code = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static Pattern pattern = Pattern.compile("^[1]\\d{10}$");

    StringBuilder str;

    /**
     * 登录页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model) {
        model.setViewName("/system/index");
        return model;
    }

    /**
     * 注册页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/rigisters", method = RequestMethod.GET)
    public ModelAndView rigisters(ModelAndView model) {
        model.setViewName("/system/rigisters");
        return model;
    }

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView main(ModelAndView model) {
        model.setViewName("/system/main");
        return model;
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "password", required = false) String password) {
        Map<String, Object> ret = new HashMap<>();
        if (StringUtils.isEmpty(username)) {
            ret.put("type", "error");
            ret.put("msg", "请填写用户名！");
            return ret;
        }
        if (StringUtils.isEmpty(password)) {
            ret.put("type", "error");
            ret.put("msg", "请填写密码！");
            return ret;
        }
        if (username.length() < 6) {
            ret.put("type", "error");
            ret.put("msg", "用户名长度不能低于6位！");
            return ret;
        }
        if (password.length() < 6) {
            ret.put("type", "error");
            ret.put("msg", "密码长度不能低于6位！");
            return ret;
        }
        Integer isSave = indexService.isExist(username);
        if (isSave == 0) {
            //账号不存在
            ret.put("type", "noSearch");
            ret.put("msg", "账号不存在！");
        } else {
            User user = indexService.findUser(username);
            if (user.getPassword().equals(password)) {
                //密码正确
                ret.put("type", "success");
                ret.put("msg", "登录成功，正在跳转主页面！");
            } else {
                //密码错误
                ret.put("type", "pwdError");
                ret.put("msg", "密码错误！");
            }
        }
        return ret;
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @RequestMapping(value = "/rigister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> rigister(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "repassword") String repassword,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "Vc") String vc) {
        Map<String, Object> ret = new HashMap<>();
        if (!validateMobilePhone(phone)) {
            ret.put("type", "error");
            ret.put("msg", "手机号格式不正确");
            return ret;
        }
        if (StringUtils.isEmpty(username)) {
            ret.put("type", "error");
            ret.put("msg", "请填写用户名！");
            return ret;
        }
        if (username.length() < 6) {
            ret.put("type", "error");
            ret.put("msg", "用户名长度不能低于6位！");
            return ret;
        }
        if (StringUtils.isEmpty(password)) {
            ret.put("type", "error");
            ret.put("msg", "请填写密码！");
            return ret;
        }
        if (password.length() < 6) {
            ret.put("type", "error");
            ret.put("msg", "密码长度不能低于6位！");
            return ret;
        }
        if (StringUtils.isEmpty(repassword)) {
            ret.put("type", "error");
            ret.put("msg", "请填写确认密码！");
            return ret;
        }
        if (StringUtils.isEmpty(phone)) {
            ret.put("type", "error");
            ret.put("msg", "请填写手机号！");
            return ret;
        }
        if (StringUtils.isEmpty(vc)) {
            ret.put("type", "error");
            ret.put("msg", "请填写手机号验证码！");
            return ret;
        }
        if (!password.equals(repassword)) {
            ret.put("type", "error");
            ret.put("msg", "密码和确认密码不一致，请重新输入！");
            return ret;
        }
        if (!vc.toLowerCase().equals(str.toString().toLowerCase())) {
            ret.put("type", "error");
            ret.put("msg", "验证码错误，请重新输入！");
            return ret;
        }
        Integer isSave = indexService.isExist(username);
        if (isSave != 0) {
            ret.put("type", "error");
            ret.put("msg", "账号已被注册！");
            return ret;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        Integer i = indexService.addUser(user);
        if (i != 0) {
            ret.put("type", "success");
            ret.put("msg", "注册成功，正在跳转登录页面！");
        } else {
            ret.put("type", "error");
            ret.put("msg", "注册失败！");
        }
        return ret;
    }

    /**
     * 正则匹配手机号
     *
     * @param in
     * @return
     */
    public static boolean validateMobilePhone(String in) {
        return pattern.matcher(in).matches();
    }

    /**
     * 获取手机验证码
     *
     * @return
     */
    @RequestMapping(value = "/getVc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getVc(
            @RequestParam(name = "phone") String phone) {
        Map<String, Object> ret = new HashMap<>();
        if (StringUtils.isEmpty(phone)) {
            ret.put("type", "error");
            ret.put("msg", "请填写手机号！");
            return ret;
        }
        if (!validateMobilePhone(phone)) {
            ret.put("type", "error");
            ret.put("msg", "手机号格式不正确");
            return ret;
        }
        str = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int ran = (int) (Math.random() * 58);
            str.append(code[ran]);
        }
        ret.put("type", "success");
        ret.put("msg", str);
        return ret;
    }
}
