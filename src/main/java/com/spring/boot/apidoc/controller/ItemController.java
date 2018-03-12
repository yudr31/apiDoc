package com.spring.boot.apidoc.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.boot.apidoc.config.ItemDocEdit;
import com.spring.boot.apidoc.config.ItemPwdVisit;
import com.spring.boot.apidoc.config.ItemVistCfg;
import com.spring.boot.apidoc.config.shiro.ShiroRealm;
import com.spring.boot.apidoc.entity.*;
import com.spring.boot.apidoc.service.*;
import com.spring.boot.apidoc.strategy.showpage.ItemPageContext;
import com.spring.boot.apidoc.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuderen
 * @version 2018/2/27 8:43
 */
@Controller
@RequestMapping("/item")
public class ItemController extends BaseController{

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDocService itemDocService;
    @Autowired
    private ItemPageContext itemPageContext;
    @Autowired
    private DocHistoryService docHistoryService;
    @Autowired
    private ItemMemberService itemMemberService;

    @RequestMapping("/goBack")
    public String goBack(){
        return "redirect:/";
    }

    @ItemDocEdit
    @RequestMapping("/setting")
    public String setting(Item item, ModelMap modelMap){
        Item result = itemService.getItemByItemId(item.getItemId());
        ItemDoc itemDoc = new ItemDoc();
        itemDoc.setItemId(item.getItemId());
        modelMap.addAttribute("item",result);
        return "item/setting";
    }

    @ItemDocEdit
    @RequestMapping("/memberList")
    public void memberList(ItemMember itemMember, HttpServletResponse response){
        List<ItemMember> itemMemberList = itemMemberService.getItemMemberListByItemId(itemMember);
        renderJson(response,JSONObject.toJSONString(successResult(itemMemberList)));
    }

    @ItemDocEdit
    @RequestMapping("/addMember")
    public void addMember(ItemMember itemMember, HttpServletResponse response){
        SysUser sysUser = sysUserService.findByUserName(itemMember.getUserName());
        if (null != sysUser){
            List<ItemMember> itemMemberList = itemMemberService.getItemMemberListByItemIdAndUserName(itemMember);
            if (!CollectionUtils.isEmpty(itemMemberList)){
                renderJson(response,JSONObject.toJSONString(errorResult("该用户已添加！")));
                return;
            }
            ItemMember result = itemMemberService.saveItemMember(itemMember);
            if (null != result && null != itemMember.getId()){
                renderJson(response,JSONObject.toJSONString(successResult("")));
                return;
            }
        }
        renderJson(response,JSONObject.toJSONString(errorResult("该用户未注册！")));
    }

    @ItemDocEdit
    @RequestMapping("/deleteMember")
    public void deleteMember(ItemMember itemMember, HttpServletResponse response){
        itemMemberService.deleteItemMemberById(itemMember.getId());
        renderJson(response,JSONObject.toJSONString(successResult("")));
    }

    @ItemDocEdit
    @RequestMapping("/detail")
    public void detail(ItemDoc itemDoc, HttpServletResponse response){
        Item item = itemService.getItemByItemId(itemDoc.getItemId());
        Map<String,Object> result = new HashMap();
        result.put("item",item);
        result.put("error_code",0);
        renderJson(response, JSONObject.toJSONString(result));
    }

    @RequestMapping("/update")
    public void update(Item item, HttpServletResponse response){
        itemService.saveItem(item);
        Map<String,Object> result = new HashMap();
        result.put("error_code",0);
        renderJson(response, JSONObject.toJSONString(result));
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "item/add";
    }

    @RequestMapping("/add")
    public String add(Item item){
        itemService.saveItem(item);
        return "redirect:/";
    }

    @RequestMapping("/visit")
    public String visit(HttpServletRequest request, HttpServletResponse response, Item item, ModelMap modelMap){
        String referUrl = request.getParameter("refer_url");
        Item result = itemService.getItemByItemId(item.getItemId());
        if (result.getPassword().equals(item.getPassword())){
            String sourceToken = itemService.getItemVisitToken(item.getItemId());
            String cookieName = ItemVistCfg.ITEM_VISIT_TOKEN + item.getItemId();
            CookieUtils.addCookie(request,response, cookieName,sourceToken,ItemVistCfg.ITEM_VISIT_TOKEN_EXPIRY,"");
            return "redirect:" + referUrl;
        }
        modelMap.addAttribute("itemId",item.getItemId());
        return "item/pwd";
    }

    @ItemPwdVisit
    @RequestMapping("/show")
    public String show(HttpServletRequest request, ItemDoc itemDoc, ModelMap modelMap){
        String keyWord = request.getParameter("keyWord");
        if (StringUtils.isNotBlank(keyWord)){
            return search(request,itemDoc,modelMap);
        }
        String itemId = request.getParameter("itemId");
        Boolean isItemMember = itemService.isItemMemberEditor(Long.parseLong(itemId));
        ShiroRealm.Principal principal = SystemUtils.getPrincipal();
        modelMap.addAttribute("user",null == principal ? "" : principal);
        modelMap.addAttribute("isItemMember",isItemMember);
        return itemPageContext.showItemPageInfo(itemDoc,modelMap);
    }

    @ItemPwdVisit
    @RequestMapping("/search")
    public String search(HttpServletRequest request, ItemDoc itemDoc, ModelMap modelMap){
        String keyWord = request.getParameter("keyWord");
        Item item = itemService.getItemByItemId(itemDoc.getItemId());
        ItemDoc showDoc = itemDocService.getItemDocByDocId(itemDoc.getDocId());
        List<ItemDocMenu> itemDocList = itemDocService.getItemDocMenuList(itemDoc);
        if (!CollectionUtils.isEmpty(itemDocList)){
            if (null == showDoc){
                showDoc = itemDocList.get(0);
            }
            String docMenu =  PageUtils.getMenuString(itemDocList,showDoc,keyWord,true);
            modelMap.addAttribute("showDoc", showDoc);
            modelMap.addAttribute("docMenu", docMenu);
            modelMap.addAttribute("keyWord", keyWord);
        }
        ShiroRealm.Principal principal = SystemUtils.getPrincipal();
        modelMap.addAttribute("item", item);
        modelMap.addAttribute("user",principal == null ? "" : principal);
        return "item/regularPage";
    }

    @ItemDocEdit
    @RequestMapping("/edit")
    public String edit(ItemDoc itemDoc, ModelMap modelMap){
        Item item = itemService.getItemByItemId(itemDoc.getItemId());
        ItemDoc editDoc = itemDocService.getItemDocByDocId(itemDoc.getDocId());
        modelMap.addAttribute("editDoc",null == editDoc ? itemDoc : editDoc);
        modelMap.addAttribute("item",item);
        if ("1".equals(item.getItemType())){
            if (null == editDoc){
                editDoc = new ItemDoc();
            }
            List<ItemDocMenu> itemDocList = itemDocService.getItemDocMenuList(itemDoc);
            String options = PageUtils.getOptions(itemDocList,editDoc);
            modelMap.addAttribute("options",options);
        }
        return "item/edit";
    }

    @ItemDocEdit
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, ItemDoc itemDoc, ModelMap modelMap){
        String showContent = request.getParameter("test-editormd-html-code");
        String editContent = request.getParameter("test-editormd-markdown-doc");
        itemDoc.setShowContent(showContent);
        itemDoc.setEditContent(editContent.trim());
        ItemDoc result = itemDocService.saveItemDoc(itemDoc);
        docHistoryService.saveDocHistory(new DocHistory(result));
        return show(request,result,modelMap);
    }

    @ItemDocEdit
    @RequestMapping("/docDetail")
    public void docDetail(ItemDoc itemDoc, HttpServletResponse response){
        ItemDoc docResult = itemDocService.getItemDocByDocId(itemDoc.getDocId());
        Map<String,Object> result = new HashMap();
        result.put("userName", UserUtil.getUserName(docResult.getUpdateUser()));
        result.put("addTime", DateUtils.convertMillisecondToLongDateString(docResult.getUpdateTime()));
        renderJson(response, JSONObject.toJSONString(result));
    }

    @ItemDocEdit
    @RequestMapping("/copy")
    public String copy(ItemDoc itemDoc, ModelMap modelMap){
        Item item = itemService.getItemByItemId(itemDoc.getItemId());
        ItemDoc editDoc = itemDocService.getItemDocByDocId(itemDoc.getDocId());
        editDoc.setDocName(editDoc.getDocName() + "-COPY");
        editDoc.setDocId(null);
        List<ItemDocMenu> itemDocList = itemDocService.getItemDocMenuList(itemDoc);
        modelMap.addAttribute("item",item);
        modelMap.addAttribute("editDoc",editDoc);
        modelMap.addAttribute("itemDocList",itemDocList);
        return "item/edit";
    }

    @ItemDocEdit
    @RequestMapping("/remove")
    public String remove(HttpServletRequest request, ItemDoc itemDoc,ModelMap modelMap){
        itemDocService.removeItemDoc(itemDoc);
        itemDoc.setDocId(null);
        return show(request,itemDoc,modelMap);
    }

    @ItemDocEdit
    @RequestMapping("/removeDoc")
    public void removeDoc(ItemDoc itemDoc, HttpServletResponse response){
        Integer count = itemDocService.updateDocStatus(itemDoc);
        if (count > 0){
            renderJson(response, JSONObject.toJSONString(successResult("")));
            return;
        }
        renderJson(response, JSONObject.toJSONString(errorResult("删除文档菜单错误")));
    }

    @ItemDocEdit
    @RequestMapping("/recoverDoc")
    public void recoverDoc(ItemDoc itemDoc, HttpServletResponse response){
        Integer count = itemDocService.updateDocStatus(itemDoc);
        if (count > 0){
            renderJson(response, JSONObject.toJSONString(successResult("")));
            return;
        }
        renderJson(response, JSONObject.toJSONString(errorResult("恢复文档菜单错误")));
    }

    @RequestMapping("/docList")
    public void docList(ItemDoc itemDoc, HttpServletResponse response){
        List<ItemDocMenu> itemDocList = itemDocService.getItemDocMenuList(itemDoc);
        String tableTrList = PageUtils.getTableTrList(itemDocList,null);
        Map<String,Object> result = new HashMap();
        result.put("tableTrList", tableTrList);
        renderJson(response, JSONObject.toJSONString(successResult(result)));
    }

    @ItemDocEdit
    @RequestMapping("/menu")
    public String menu(ItemDoc itemDoc, ModelMap modelMap){
        ItemDoc editDoc = itemDocService.getItemDocByDocId(itemDoc.getDocId());
        List<ItemDocMenu> itemDocList = itemDocService.getItemDocMenuList(itemDoc);
        String options = PageUtils.getOptions(itemDocList,editDoc);
        modelMap.addAttribute("editDoc",editDoc);
        modelMap.addAttribute("options",options);
        return "item/menu";
    }

    @ItemDocEdit
    @RequestMapping("/saveMenu")
    public void saveMenu(HttpServletResponse response, ItemDoc itemDoc){
        if (itemDoc.getParentId() == 0){
            itemDoc.setParentId(null);
        }
        Integer updateCount = itemDocService.updateMenu(itemDoc);
        if (updateCount > 0){
            renderJson(response, JSONObject.toJSONString(successResult("")));
            return;
        }
        renderJson(response, JSONObject.toJSONString(errorResult("更新菜单失败")));
    }

    @ItemDocEdit
    @RequestMapping("/deleteMenu")
    public void deleteMenu(HttpServletResponse response, ItemDoc itemDoc){
        renderJson(response, JSONObject.toJSONString(successResult("")));
    }

    @ItemDocEdit
    @RequestMapping("/history")
    public String history(ItemDoc itemDoc, ModelMap modelMap){
        List<DocHistory> docHistoryList = docHistoryService.getAllListByDocId(itemDoc.getDocId());
        modelMap.addAttribute("docHistoryList",docHistoryList);
        modelMap.addAttribute("itemDoc",itemDoc);
        return "item/history";
    }

    @ItemDocEdit
    @RequestMapping("/preview")
    public String preview(DocHistory docHistory, ModelMap modelMap){
        DocHistory preview = docHistoryService.getDocHistoryByHisId(docHistory.getHisId());
        return edit(preview.getItemDoc(),modelMap);
    }

}
