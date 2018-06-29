package auto.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import auto.test.EnvironmentContext;

@Controller
public class HomeController {

  public static int currentStep;
  Logger logger = LoggerFactory.getLogger(HomeController.class);
  @Autowired
  EnvironmentContext context;

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

}
