// JavaScript Document
function CreateDiv(tabid, url, name)
                 {
                        ///�����ǰtabid����ֱ����ʾ�Ѿ��򿪵�tab
                        if (document.getElementById("div_" + tabid) == null)
                        {
                                //����iframe
                                var box = document.createElement("iframe");
                                box.id = "div_" + tabid;
                                box.src = url;
                                box.height = "100%";
                                box.frameBorder = 0;
                                box.width = "100%";
                                document.getElementById("div_pannel").appendChild(box);
                
                                //�����������ʼ���ڵ�tab��ǰЧ������������ʾ��div
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
                
                                //����li�˵�
                                var tab = document.createElement("li");
                                tab.className = "crent";
                                tab.id = tabid;
                                var litxt = "<span><a href=\"javascript:;\" onclick=\"javascript:CreateDiv('" + tabid + "','" + url + "','" + name + "')\" title=" + name + " class=\"menua\">" + name + "</a><a onclick=\"RemoveDiv('" + tabid + "')\" class=\"win_close\" title=\"�رյ�ǰ����\"><a></span>";
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