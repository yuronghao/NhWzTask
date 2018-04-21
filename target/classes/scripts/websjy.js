// JavaScript Document
function CreateDiv(tabid, url, name)
                 {
                        ///如果当前tabid存在直接显示已经打开的tab
                        if (document.getElementById("div_" + tabid) == null)
                        {
                                //创建iframe
                                var box = document.createElement("iframe");
                                box.id = "div_" + tabid;
                                box.src = url;
                                box.height = "100%";
                                box.frameBorder = 0;
                                box.width = "100%";
                                document.getElementById("div_pannel").appendChild(box);
                
                                //遍历并清除开始存在的tab当前效果并隐藏其显示的div
                                var tablist = document.getElementById("div_tab").getElementsByTagName('li');
                                var pannellist = document.getElementById("div_pannel").getElementsByTagName('iframe');
                                if (tablist.length > 0)
                                {
                                        for (i = 0; i < tablist.length; i++)
                                        {
                                                tablist[i].className = "";
                                                pannellist[i].style.display = "none";
                                        }
                                }
                
                                //创建li菜单
                                var tab = document.createElement("li");
                                tab.className = "crent";
                                tab.id = tabid;
                                var litxt = "<span><a href=\"javascript:;\" onclick=\"javascript:CreateDiv('" + tabid + "','" + url + "','" + name + "')\" title=" + name + " class=\"menua\">" + name + "</a><a onclick=\"RemoveDiv('" + tabid + "')\" class=\"win_close\" title=\"关闭当前窗口\"><a></span>";
                                tab.innerHTML = litxt;
                                document.getElementById("div_tab").appendChild(tab);
                        }
                        else
                        {
                                var tablist = document.getElementById("div_tab").getElementsByTagName('li');
                                var pannellist = document.getElementById("div_pannel").getElementsByTagName('iframe');
                                //alert(tablist.length);
                                for (i = 0; i < tablist.length; i++)
                                {
                                        tablist[i].className = "";
                                        pannellist[i].style.display = "none"
                                }
                                document.getElementById(tabid).className = 'crent';
                                document.getElementById("div_" + tabid).style.display = 'block';
                        }
                }
                function RemoveDiv(obj)
                 {
                        var ob = document.getElementById(obj);
                        ob.parentNode.removeChild(ob);
                        var obdiv = document.getElementById("div_" + obj);
                        obdiv.parentNode.removeChild(obdiv);
                        var tablist = document.getElementById("div_tab").getElementsByTagName('li');
                        var pannellist = document.getElementById("div_pannel").getElementsByTagName('iframe');
                        if (tablist.length > 0)
                        {
                                tablist[tablist.length - 1].className = 'crent';
                                pannellist[tablist.length - 1].style.display = 'block';
                        }                
                }