package auto.test.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.Yaml;

import auto.test.Admin;
import auto.test.EnvironmentContext;
import auto.test.alarm.OriginAlarmService;
import auto.test.mapper.AdminMapper;
import auto.test.selenium.Procedure;

@Controller
public class HomeController {

  public static int currentStep;
  Logger logger = LoggerFactory.getLogger(HomeController.class);
  @Autowired
  EnvironmentContext context;

  @Autowired
  AdminMapper adminMapper;

  @Autowired
  OriginAlarmService originAlarmService;

  @RequestMapping("/")
  public String index() {
    currentStep = 0;
    return "redirect:/index";

  }

  @RequestMapping("/index")
  public String index(Model model, @RequestParam(value = "step", required = false) Integer step) {
    logger.info("current step is " + step);
    currentStep = step != null ? step : currentStep + 1;
    model.addAttribute("currentStep", currentStep);
//    List<OriginAlarmBean> originAlarmBeanList = new ArrayList<OriginAlarmBean>();
//
//    try{
//      originAlarmBeanList = originAlarmService.selectAllAlarm();
//    }catch (Exception e){
//      e.printStackTrace();
//    }
//    model.addAttribute(originAlarmBeanList);
    return "redirect:/alarm";
  }

  @RequestMapping(value = "/admin", method = RequestMethod.GET)
  public String admin(Model model) {
    logger.info("access admin start...");
    List<Admin> admins = adminMapper.selectAll();
    Admin admin = new Admin();

    if (CollectionUtils.isNotEmpty(admins)) {
      admin.setProcedures(admins.get(0).getProcedures());
    }
    Yaml yaml = new Yaml();
    List<Procedure> procedures = yaml.load(admin.getProcedures());
    logger.info("\n"+yaml.dump(procedures));

    model.addAttribute("admin", admin);
    return "admin";
  }

  @RequestMapping(value = "/admin", method = RequestMethod.POST)
  public String admin(Model model,
      @RequestParam(value = "procedures", required = false) String procedures) {
    List<Admin> admins = adminMapper.selectAll();
    Admin admin = new Admin();
    admin.setProcedures(procedures);
    if (CollectionUtils.isNotEmpty(admins)) {
      adminMapper.update(admin);
    } else {
      adminMapper.insert(admin);
    }
    model.addAttribute("admin", admin);
    return "redirect:/admin";
  }

}
