package rest.controls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rest.models.appservices.ShohinAppService;
import rest.models.appservices.dtos.ShohinDto;

@RestController
public class ShohinRestController {
    @Autowired
    ShohinAppService service;

    @RequestMapping(value = "/rest/read", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShohinDto> readAll(Model model) {
        model.addAttribute("title", "商品一覧表");
        List<ShohinDto> data = service.getAllShohinList();
        model.addAttribute("data", data);
        return data;
    }

    @RequestMapping(value = "/rest/create", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Validated ShohinDto shohin) {
        service.registerShohin(String.valueOf(shohin.getShohinCode()), shohin.getShohinName(), shohin.getRemarks());
    }

    @PutMapping("/rest/{uniqueId}")
    @ResponseBody
    public void change(@PathVariable("uniqueId")String uniqueId, @RequestBody @Validated ShohinDto shohin) {
        service.editShohin(uniqueId, String.valueOf(shohin.getShohinCode()), shohin.getShohinName(), shohin.getRemarks());
    }

    @DeleteMapping("/rest/{uniqueId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void erase(@PathVariable String uniqueId) {
        service.removeShohin(uniqueId);
    }
}