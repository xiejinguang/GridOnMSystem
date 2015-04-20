function autoFitDocSize(iframe) {
    doc = $(iframe).contents().find("body");
    $(iframe).height(doc.height());
    doc.on("DOMSubtreeModified", function(e) {
        $(iframe).height(doc.height());
        //alert("you see it");
    });
    //alert("you see it");
}

function autoFitDocSize1(iframe) {
    doc = iframe.contentWindow.document;
    iframe.height = doc.body.scrollHeight;
    doc.on("DOMSubtreeModified", function(e) {
        $(iframe).height(doc.height());
    });
}

function fitViewport(source) {
    var dialog = $(source);
    var content = dialog.children('.ui-dialog-content');
    dialog.height(Math.min($(window).height(), content.height() + 50));
    content.height(Math.min($(window).height() - 50, content.height()));

}
//日历、行程本地化
PrimeFaces.locales['zh_CN'] = {
    closeText: '关闭',
    prevText: '上个月',
    nextText: '下个月',
    currentText: '当前',
    monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
    monthNamesShort: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
    dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
    dayNamesShort: ['日','一','二','三','四','五','六'],
    dayNamesMin: ['日','一','二','三','四','五','六'],
    weekHeader: '周',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: true,
    yearSuffix: '', // 年
    timeOnlyTitle: '仅时间',
    timeText: '时间',
    hourText: '时',
    minuteText: '分',
    secondText: '秒',
    ampm: false,
    month: '月',
    week: '周',
    day: '日',
    allDayText : '全天'
};


//Override the default Primefaces.dialog.DialogHandler
PrimeFaces.dialog.DialogHandler = {openDialog: function(e) {
        var d = e.sourceComponentId + "_dlg";
        if (document.getElementById(d)) {
            return
        }
        var h = e.sourceComponentId.replace(/:/g, "_") + "_dlgwidget", f = $('<div id="' + d + '" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-shadow ui-overlay-hidden" data-pfdlgcid="' + e.pfdlgcid + '" data-widgetvar="' + h + '"></div>').append('<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-top"><span class="ui-dialog-title"></span></div>');
        if (e.options.closable !== false) {
            f.children(".ui-dialog-titlebar").append('<a class="ui-dialog-titlebar-icon ui-dialog-titlebar-close ui-corner-all" href="#" role="button"><span class="ui-icon ui-icon-closethick"></span></a>')
        }
        f.append('<div class="ui-dialog-content ui-widget-content"><iframe frameborder="0" scrolling="no"  width="100%"  onload="autoFitDocSize(this)" /></div>');
        f.appendTo(document.body);
        var c = f.find("iframe"), g = e.url.indexOf("?") === -1 ? "?" : "&", b = e.url + g + "pfdlgcid=" + e.pfdlgcid, a = e.options.contentWidth || 640;
        var dialogCSS = {};
        if (e.options.minWidth) {
            dialogCSS.minWidth = e.options.minWidth;
        }
        if (e.options.minHeight) {
            dialogCSS.minHeight = e.options.minHeight;
        }
        f.css(dialogCSS);

        //c.width(a);
        var contentCSS = {};
        if (e.options.contentMinWidth) {
            contentCSS.minWidth = e.options.contentMinWidth;
        }
        if (e.options.contentMinHeight) {
            contentCSS.minHeight = e.options.contentMinHeight;
        }
//       // contentCSS.width = "100%";
        c.css(contentCSS);


        c.on("load", function() {
            var j = $(this), k = j.contents().find("title");
            if (!j.data("initialized")) {
                PrimeFaces.cw("Dialog", h, {id: d, position: "center", sourceComponentId: e.sourceComponentId, sourceWidget: e.sourceWidget, onHide: function() {
                        var l = this;
                        this.destroyIntervalId = setInterval(function() {
                            var m = l.content.children("iframe");
                            if (m.get(0).contentWindow.PrimeFaces.ajax.Queue.isEmpty()) {
                                clearInterval(l.destroyIntervalId);
                                m.attr("src", "about:blank");
                                l.jq.remove()
                            }
                        }, 10);
                        PF[h] = undefined
                    }, modal: e.options.modal, resizable: e.options.resizable, draggable: e.options.draggable, width: e.options.width || 450, height: e.options.height || 300})
            }
            if (k.length > 0) {
                PF(h).titlebar.children("span.ui-dialog-title").html(k.text())
            }
            var i = e.options.contentHeight || j.get(0).contentWindow.document.body.scrollHeight + 5;
            j.height(i);
            PF(h).show();
            c.data("initialized", true)
        }).attr("src", b)
    }, closeDialog: function(cfg) {
        var dlg = $(document.body).children("div.ui-dialog").filter(function() {
            return $(this).data("pfdlgcid") === cfg.pfdlgcid
        }), dlgWidget = PF(dlg.data("widgetvar")), sourceWidget = dlgWidget.cfg.sourceWidget, sourceComponentId = dlgWidget.cfg.sourceComponentId, dialogReturnBehavior = null;
        if (sourceWidget && sourceWidget.cfg.behaviors) {
            dialogReturnBehavior = sourceWidget.cfg.behaviors.dialogReturn
        } else {
            if (sourceComponentId) {
                var dialogReturnBehaviorStr = $(document.getElementById(sourceComponentId)).data("dialogreturn");
                if (dialogReturnBehaviorStr) {
                    dialogReturnBehavior = eval("(function(){" + dialogReturnBehaviorStr + "})")
                }
            }
        }
        if (dialogReturnBehavior) {
            var ext = {params: [{name: sourceComponentId + "_pfdlgcid", value: cfg.pfdlgcid}]};
            dialogReturnBehavior.call(this, ext)
        }
        dlgWidget.hide()
    }, showMessageInDialog: function(b) {
        if (!this.messageDialog) {
            var a = $('<div id="primefacesmessagedlg" class="ui-message-dialog ui-dialog ui-widget ui-widget-content ui-corner-all ui-shadow ui-overlay-hidden"/>').append('<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-top"><span class="ui-dialog-title"></span><a class="ui-dialog-titlebar-icon ui-dialog-titlebar-close ui-corner-all" href="#" role="button"><span class="ui-icon ui-icon-closethick"></span></a></div><div class="ui-dialog-content ui-widget-content" style="height: auto;"></div>').appendTo(document.body);
            PrimeFaces.cw("Dialog", "primefacesmessagedialog", {id: "primefacesmessagedlg", modal: true, draggable: false, resizable: false, showEffect: "fade", hideEffect: "fade"});
            this.messageDialog = PF("primefacesmessagedialog");
            this.messageDialog.titleContainer = this.messageDialog.titlebar.children("span.ui-dialog-title")
        }
        this.messageDialog.titleContainer.text(b.summary);
        this.messageDialog.content.html("").append('<span class="ui-dialog-message ui-messages-' + b.severity.split(" ")[0].toLowerCase() + '-icon" />').append(b.detail);
        this.messageDialog.show()
    }, confirm: function(a) {
        if (PrimeFaces.confirmDialog) {
            PrimeFaces.confirmSource = $(PrimeFaces.escapeClientId(a.source));
            PrimeFaces.confirmDialog.showMessage(a)
        } else {
            PrimeFaces.warn("No global confirmation dialog available.")
        }
    }};
PrimeFaces.widget.Dialog = PrimeFaces.widget.BaseWidget.extend({init: function(a) {
        this._super(a);
        this.content = this.jq.children(".ui-dialog-content");
        this.titlebar = this.jq.children(".ui-dialog-titlebar");
        this.footer = this.jq.find(".ui-dialog-footer");
        this.icons = this.titlebar.children(".ui-dialog-titlebar-icon");
        this.closeIcon = this.titlebar.children(".ui-dialog-titlebar-close");
        this.minimizeIcon = this.titlebar.children(".ui-dialog-titlebar-minimize");
        this.maximizeIcon = this.titlebar.children(".ui-dialog-titlebar-maximize");
        this.blockEvents = "focus." + this.id + " mousedown." + this.id + " mouseup." + this.id;
        this.cfg.width = this.cfg.width || "auto";
        this.cfg.height = this.cfg.height || "auto";
        this.cfg.draggable = this.cfg.draggable === false ? false : true;
        this.cfg.resizable = this.cfg.resizable === false ? false : true;
        this.cfg.minWidth = this.cfg.minWidth || 150;
        this.cfg.minHeight = this.cfg.minHeight || this.titlebar.outerHeight();
        this.cfg.position = this.cfg.position || "center";
        this.parent = this.jq.parent();
        this.initSize();
        this.bindEvents();
        if (this.cfg.draggable) {
            this.setupDraggable()
        }
        if (this.cfg.resizable) {
            this.setupResizable()
        }
        if (this.cfg.modal) {
            this.syncWindowResize()
        }
        if (this.cfg.appendTo) {
            this.jq.appendTo(PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(this.cfg.appendTo))
        }
        if ($(document.body).children(".ui-dialog-docking-zone").length === 0) {
            $(document.body).append('<div class="ui-dialog-docking-zone"></div>')
        }
        var b = $(this.jqId + "_modal");
        if (b.length > 0) {
            b.remove()
        }
        this.applyARIA();
        if (this.cfg.visible) {
            this.show()
        }
    }, refresh: function(a) {
        this.positionInitialized = false;
        this.loaded = false;
        $(document).off("keydown.dialog_" + a.id);
        if (a.appendTo) {
            var b = $(this.jqId);
            if (b.length > 1) {
                PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(a.appendTo).children(this.jqId).remove()
            }
        }
        this.init(a)
    }, initSize: function() {
        this.jq.css({width: this.cfg.width, height: "auto"});
        this.content.height(this.cfg.height);
        if (this.cfg.fitViewport) {
            this.fitViewport()
        }
        if (this.cfg.width === "auto" && PrimeFaces.isIE(7)) {
            this.jq.width(this.content.outerWidth())
        }
    }, fitViewport: function() {
        var b = $(window).height(), a = this.content.innerHeight() - this.content.height();
        if (this.jq.innerHeight() > b) {
            this.content.height(b - this.titlebar.innerHeight() - a)
        }
    }, enableModality: function() {
        var b = this, a = $(document);
        $(document.body).append('<div id="' + this.id + '_modal" class="ui-widget-overlay"></div>').children(this.jqId + "_modal").css({width: a.width(), height: a.height(), "z-index": this.jq.css("z-index") - 1});
        a.on("keydown." + this.id, function(e) {
            var f = $(e.target);
            if (e.keyCode === $.ui.keyCode.TAB) {
                var d = b.content.find(":tabbable").add(b.footer.find(":tabbable"));
                if (d.length) {
                    var g = d.filter(":first"), c = d.filter(":last");
                    if (f.is(document.body)) {
                        g.focus(1);
                        e.preventDefault()
                    } else {
                        if (e.target === c[0] && !e.shiftKey) {
                            g.focus(1);
                            e.preventDefault()
                        } else {
                            if (e.target === g[0] && e.shiftKey) {
                                c.focus(1);
                                e.preventDefault()
                            }
                        }
                    }
                }
            } else {
                if (!f.is(document.body) && (f.zIndex() < b.jq.zIndex())) {
                    e.preventDefault()
                }
            }
        }).on(this.blockEvents, function(c) {
            if ($(c.target).zIndex() < b.jq.zIndex()) {
                c.preventDefault()
            }
        })
    }, disableModality: function() {
        $(document.body).children(this.jqId + "_modal").remove();
        $(document).off(this.blockEvents).off("keydown." + this.id)
    }, syncWindowResize: function() {
        $(window).resize(function() {
            $(document.body).children(".ui-widget-overlay").css({width: $(document).width(), height: $(document).height()})
        })
    }, show: function() {
        if (this.jq.hasClass("ui-overlay-visible")) {
            return
        }
        if (!this.loaded && this.cfg.dynamic) {
            this.loadContents()
        } else {
            if (!this.positionInitialized) {
                this.initPosition()
            }
            this._show()
        }
    }, _show: function() {
        this.jq.removeClass("ui-overlay-hidden").addClass("ui-overlay-visible").css({display: "none", visibility: "visible"});
        this.moveToTop();
        if (this.cfg.showEffect) {
            var a = this;
            this.jq.show(this.cfg.showEffect, null, "normal", function() {
                a.postShow()
            })
        } else {
            this.jq.show();
            this.postShow()
        }
        if (this.cfg.modal) {
            this.enableModality()
        }
    }, postShow: function() {
        if (this.cfg.onShow) {
            this.cfg.onShow.call(this)
        }
        this.jq.attr({"aria-hidden": false, "aria-live": "polite"});
        this.applyFocus()
    }, hide: function() {
        if (this.jq.hasClass("ui-overlay-hidden")) {
            return
        }
        if (this.cfg.hideEffect) {
            var a = this;
            this.jq.hide(this.cfg.hideEffect, null, "normal", function() {
                if (a.cfg.modal) {
                    a.disableModality()
                }
                a.onHide()
            })
        } else {
            this.jq.hide();
            if (this.cfg.modal) {
                this.disableModality()
            }
            this.onHide()
        }
    }, applyFocus: function() {
        if (this.cfg.focus) {
            PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(this.cfg.focus).focus()
        } else {
            this.jq.find(":not(:submit):not(:button):not(:radio):not(:checkbox):input:visible:enabled:first").focus()
        }
    }, bindEvents: function() {
        var a = this;
        this.jq.mousedown(function(b) {
            if (!$(b.target).data("primefaces-overlay-target")) {
                a.moveToTop()
            }
        });
        this.icons.mouseover(function() {
            $(this).addClass("ui-state-hover")
        }).mouseout(function() {
            $(this).removeClass("ui-state-hover")
        });
        this.closeIcon.click(function(b) {
            a.hide();
            b.preventDefault()
        });
        this.maximizeIcon.click(function(b) {
            a.toggleMaximize();
            b.preventDefault()
        });
        this.minimizeIcon.click(function(b) {
            a.toggleMinimize();
            b.preventDefault()
        });
        if (this.cfg.closeOnEscape) {
            $(document).on("keydown.dialog_" + this.id, function(d) {
                var c = $.ui.keyCode, b = parseInt(a.jq.css("z-index")) === PrimeFaces.zindex;
                if (d.which === c.ESCAPE && a.jq.hasClass("ui-overlay-visible") && b) {
                    a.hide()
                }
            })
        }
    }, setupDraggable: function() {
        this.jq.draggable({cancel: ".ui-dialog-content, .ui-dialog-titlebar-close", handle: ".ui-dialog-titlebar", containment: "document"})
    }, setupResizable: function() {
        var a = this;
        this.jq.resizable({handles: "n,s,e,w,ne,nw,se,sw", minWidth: this.cfg.minWidth, minHeight: this.cfg.minHeight, alsoResize: this.content, containment: "document", start: function(b, c) {
                a.jq.data("offset", a.jq.offset())
            }, stop: function(b, c) {
                var d = a.jq.data("offset");
                a.jq.css("position", "fixed");
                a.jq.offset(d)
            }});
        this.resizers = this.jq.children(".ui-resizable-handle")
    }, initPosition: function() {
        this.jq.css({left: 0, top: 0});
        if (/(center|left|top|right|bottom)/.test(this.cfg.position)) {
            this.cfg.position = this.cfg.position.replace(",", " ");
            this.jq.position({my: "center", at: this.cfg.position, collision: "fit", of: window, using: function(f) {
                    var d = f.left < 0 ? 0 : f.left, e = f.top < 0 ? 0 : f.top;
                    $(this).css({left: d, top: e})
                }})
        } else {
            var b = this.cfg.position.split(","), a = $.trim(b[0]), c = $.trim(b[1]);
            this.jq.offset({left: a, top: c})
        }
        this.positionInitialized = true
    }, onHide: function(b, c) {
        this.jq.removeClass("ui-overlay-visible").addClass("ui-overlay-hidden").css({display: "block", visibility: "hidden"});
        if (this.cfg.behaviors) {
            var a = this.cfg.behaviors.close;
            if (a) {
                a.call(this)
            }
        }
        this.jq.attr({"aria-hidden": true, "aria-live": "off"});
        if (this.cfg.onHide) {
            this.cfg.onHide.call(this, b, c)
        }
    }, moveToTop: function() {
        this.jq.css("z-index", ++PrimeFaces.zindex)
    }, toggleMaximize: function() {
        if (this.minimized) {
            this.toggleMinimize()
        }
        if (this.maximized) {
            this.jq.removeClass("ui-dialog-maximized");
            this.restoreState();
            this.maximizeIcon.children(".ui-icon").removeClass("ui-icon-newwin").addClass("ui-icon-extlink");
            this.maximized = false
        } else {
            this.saveState();
            var b = $(window);
            this.jq.addClass("ui-dialog-maximized").css({width: b.width() - 6, height: b.height()}).offset({top: b.scrollTop(), left: b.scrollLeft()});
            this.content.css({width: "auto", height: "auto"});
            this.maximizeIcon.removeClass("ui-state-hover").children(".ui-icon").removeClass("ui-icon-extlink").addClass("ui-icon-newwin");
            this.maximized = true;
            if (this.cfg.behaviors) {
                var a = this.cfg.behaviors.maximize;
                if (a) {
                    a.call(this)
                }
            }
        }
    }, toggleMinimize: function() {
        var a = true, c = $(document.body).children(".ui-dialog-docking-zone");
        if (this.maximized) {
            this.toggleMaximize();
            a = false
        }
        var b = this;
        if (this.minimized) {
            this.jq.appendTo(this.parent).removeClass("ui-dialog-minimized").css({position: "fixed", "float": "none"});
            this.restoreState();
            this.content.show();
            this.minimizeIcon.removeClass("ui-state-hover").children(".ui-icon").removeClass("ui-icon-plus").addClass("ui-icon-minus");
            this.minimized = false;
            if (this.cfg.resizable) {
                this.resizers.show()
            }
        } else {
            this.saveState();
            if (a) {
                this.jq.effect("transfer", {to: c, className: "ui-dialog-minimizing"}, 500, function() {
                    b.dock(c);
                    b.jq.addClass("ui-dialog-minimized")
                })
            } else {
                this.dock(c)
            }
        }
    }, dock: function(a) {
        this.jq.appendTo(a).css("position", "static");
        this.jq.css({height: "auto", width: "auto", "float": "left"});
        this.content.hide();
        this.minimizeIcon.removeClass("ui-state-hover").children(".ui-icon").removeClass("ui-icon-minus").addClass("ui-icon-plus");
        this.minimized = true;
        if (this.cfg.resizable) {
            this.resizers.hide()
        }
        if (this.cfg.behaviors) {
            var b = this.cfg.behaviors.minimize;
            if (b) {
                b.call(this)
            }
        }
    }, saveState: function() {
        this.state = {width: this.jq.width(), height: this.jq.height(), contentWidth: this.content.width(), contentHeight: this.content.height()};
        var a = $(window);
        this.state.offset = this.jq.offset();
        this.state.windowScrollLeft = a.scrollLeft();
        this.state.windowScrollTop = a.scrollTop()
    }, restoreState: function() {
        this.jq.width(this.state.width).height(this.state.height);
        this.content.width(this.state.contentWidth).height(this.state.contentHeight);
        var a = $(window);
        this.jq.offset({top: this.state.offset.top + (a.scrollTop() - this.state.windowScrollTop), left: this.state.offset.left + (a.scrollLeft() - this.state.windowScrollLeft)})
    }, loadContents: function() {
        var b = this, a = {source: this.id, process: this.id, update: this.id, params: [{name: this.id + "_contentLoad", value: true}], onsuccess: function(e, c, d) {
                PrimeFaces.ajax.Response.handle(e, c, d, {widget: b, handle: function(f) {
                        this.content.html(f)
                    }});
                return true
            }, oncomplete: function() {
                b.loaded = true;
                b.show()
            }};
        PrimeFaces.ajax.Request.handle(a)
    }, applyARIA: function() {
        this.jq.attr({role: "dialog", "aria-labelledby": this.id + "_title", "aria-hidden": !this.cfg.visible});
        this.titlebar.children("a.ui-dialog-titlebar-icon").attr("role", "button")
    }});
PrimeFaces.widget.ConfirmDialog = PrimeFaces.widget.Dialog.extend({init: function(cfg) {
        cfg.draggable = false;
        cfg.resizable = false;
        cfg.modal = true;
        if (!cfg.appendTo && cfg.global) {
            cfg.appendTo = "@(body)"
        }
        this._super(cfg);
        this.title = this.titlebar.children(".ui-dialog-title");
        this.message = this.content.children(".ui-confirm-dialog-message");
        this.icon = this.content.children(".ui-confirm-dialog-severity");
        if (this.cfg.global) {
            PrimeFaces.confirmDialog = this;
            this.jq.find(".ui-confirmdialog-yes").on("click.ui-confirmdialog", function(e) {
                if (PrimeFaces.confirmSource) {
                    var fn = eval("(function(element,event){" + PrimeFaces.confirmSource.data("pfconfirmcommand") + "})");
                    fn.call(PrimeFaces.confirmSource, PrimeFaces.confirmSource.get(0), e);
                    PrimeFaces.confirmDialog.hide();
                    PrimeFaces.confirmSource = null
                }
                e.preventDefault()
            });
            this.jq.find(".ui-confirmdialog-no").on("click.ui-confirmdialog", function(e) {
                PrimeFaces.confirmDialog.hide();
                PrimeFaces.confirmSource = null;
                e.preventDefault()
            })
        }
    }, applyFocus: function() {
        this.jq.find(":button,:submit").filter(":visible:enabled").eq(0).focus()
    }, showMessage: function(a) {
        if (a.header) {
            this.title.text(a.header)
        }
        if (a.message) {
            this.message.text(a.message)
        }
        if (a.icon) {
            this.icon.removeClass().addClass("ui-icon ui-confirm-dialog-severity " + a.icon)
        }
        this.show()
    }});

