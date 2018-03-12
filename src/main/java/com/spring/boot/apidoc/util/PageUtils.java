package com.spring.boot.apidoc.util;

import com.spring.boot.apidoc.entity.ItemDoc;
import com.spring.boot.apidoc.entity.ItemDocMenu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/2 15:56
 */
public class PageUtils {

    /**
     * 获取展示页面的菜单列表
     * @param itemDocMenuList
     * @param showDoc
     * @return
     */
    public static String getMenuString(List<ItemDocMenu> itemDocMenuList, ItemDoc showDoc){
        StringBuffer stringBuffer = new StringBuffer();
        if (!CollectionUtils.isEmpty(itemDocMenuList)){
            stringBuffer.append("<ul class=\"nav nav-list bs-docs-sidenav\">\n" );
            for (int i=0; i<itemDocMenuList.size(); i++){
                ItemDocMenu menu = itemDocMenuList.get(i);
                String active = menu.getDocId() == showDoc.getDocId() ? "class=\"active\"" : "";
                stringBuffer.append("   <li " + active + ">\n");
                stringBuffer.append("       <a href=\"show?itemId="+ menu.getItemId() +"&docId="+ menu.getDocId() +"\">");
                stringBuffer.append("           <i class=\"icon-blank\"></i>").append(menu.getDocName());
                stringBuffer.append("       </a>\n");
                if(!CollectionUtils.isEmpty(menu.getChildren())){
                    stringBuffer.append(getMenuString(menu.getChildren(),showDoc));
                }
                stringBuffer.append("   </li>\n");
            }
            stringBuffer.append("</ul>\n");
        }
        return stringBuffer.toString();
    }

    /**
     * 通过关键词获取展示页面的菜单列表
     * @param itemDocMenuList   项目所有菜单列表
     * @param showDoc   当前展示菜单项
     * @param keyWord   搜索关键词
     * @param ulFlag    添加ul标签标识 true-添加ul标签，false-不添加
     * @return
     */
    public static String getMenuString(List<ItemDocMenu> itemDocMenuList, ItemDoc showDoc, String keyWord, Boolean ulFlag){
        if (StringUtils.isBlank(keyWord)){
            return getMenuString(itemDocMenuList,showDoc);
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (!CollectionUtils.isEmpty(itemDocMenuList)){
            if (ulFlag){
                stringBuffer.append("<ul class=\"nav nav-list bs-docs-sidenav\">\n" );
            }
            for (int i=0; i<itemDocMenuList.size(); i++){
                ItemDocMenu menu = itemDocMenuList.get(i);
                if (menu.getDocName().contains(keyWord)){
                    String active = menu.getDocId() == showDoc.getDocId() ? "class=\"active\"" : "";
                    stringBuffer.append("   <li " + active + ">\n");
                    stringBuffer.append("       <a href=\"show?itemId="+ menu.getItemId() +"&docId="+ menu.getDocId() +"&keyWord=" + keyWord + "\">");
                    stringBuffer.append("           <i class=\"icon-blank\"></i>").append(menu.getDocName());
                    stringBuffer.append("       </a>\n");
                    if(!CollectionUtils.isEmpty(menu.getChildren())){
                        stringBuffer.append(getMenuString(menu.getChildren(),showDoc,keyWord,true));
                    }
                    stringBuffer.append("   </li>\n");
                } else {
                    stringBuffer.append(getMenuString(menu.getChildren(),showDoc,keyWord,false));
                }
            }
            if (ulFlag){
                stringBuffer.append("</ul>\n");
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 获取编辑业务父级菜单页面的下拉选项
     * @param itemDocMenuList
     * @param editDoc
     * @return
     */
    public static String getOptions(List<ItemDocMenu> itemDocMenuList, ItemDoc editDoc){
        StringBuffer stringBuffer = new StringBuffer();
        if (!CollectionUtils.isEmpty(itemDocMenuList)){
            for (int i=0; i<itemDocMenuList.size(); i++){
                ItemDocMenu option = itemDocMenuList.get(i);
                String selected = option.getDocId() == editDoc.getParentId() ? "selected=selected" : "";
                stringBuffer.append("<option value=\""+option.getDocId()+"\" "+ selected +">"+ option.getDocName() +"</option>");
                if(!CollectionUtils.isEmpty(option.getChildren())){
                    stringBuffer.append(getOptions(option.getChildren(),editDoc));
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String getTableTrList(List<ItemDocMenu> itemDocMenuList, ItemDocMenu parentDoc){
        StringBuffer stringBuffer = new StringBuffer();
        if (!CollectionUtils.isEmpty(itemDocMenuList)){
            for (ItemDocMenu itemdocMenu : itemDocMenuList){
                stringBuffer.append("<tr>\n");

                stringBuffer.append("   <td>\n");
                stringBuffer.append("       <div class=\"type-parent\">");
                stringBuffer.append(itemdocMenu.getDocId());
                stringBuffer.append("       </div>\n");
                stringBuffer.append("    </td>\n");

                stringBuffer.append("   <td>\n");
                stringBuffer.append("       <div class=\"type-parent\">");
                stringBuffer.append(itemdocMenu.getDocName());
                stringBuffer.append("       </div>\n");
                stringBuffer.append("    </td>\n");

                stringBuffer.append("   <td>\n");
                stringBuffer.append("       <div class=\"type-parent\">");
                stringBuffer.append(null == parentDoc? "" : parentDoc.getDocName());
                stringBuffer.append("       </div>\n");
                stringBuffer.append("    </td>\n");

                String clazz = 1 == itemdocMenu.getStatus() ? "remove" : "recover";
                String operation = 1 == itemdocMenu.getStatus() ? "删除" : "恢复";

                stringBuffer.append("   <td>\n");
                stringBuffer.append("       <a href=\"#\" class=\"doc-"+ clazz +"\" data-id=\""+itemdocMenu.getDocId()+"\">");
                stringBuffer.append(operation);
                stringBuffer.append("       </a>\n");
                stringBuffer.append("    </td>\n");

                stringBuffer.append("</tr>");
                if (!CollectionUtils.isEmpty(itemdocMenu.getChildren())){
                    stringBuffer.append(getTableTrList(itemdocMenu.getChildren(),itemdocMenu));
                }
            }
        }
        return stringBuffer.toString();
    }

}
