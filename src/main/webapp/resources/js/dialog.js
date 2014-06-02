PrimeFaces.dialog = {};
PrimeFaces.dialog.DialogHandler = {
    openDialog: function(e) {
        var d = e.sourceComponentId + "_dlg";
        if (document.getElementById(d)) {
            return
        }
        var h = e.sourceComponentId.replace(/:/g, "_") + "_dlgwidget", f = $('<div id="' + d + '" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-shadow ui-overlay-hidden" data-pfdlgcid="' + e.pfdlgcid + '" data-widgetvar="' + h + '"></div>').append('<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-top"><span class="ui-dialog-title"></span></div>');
        if (e.options.closable !== false) {
            f.children(".ui-dialog-titlebar").append('<a class="ui-dialog-titlebar-icon ui-dialog-titlebar-close ui-corner-all" href="#" role="button"><span class="ui-icon ui-icon-closethick"></span></a>')
        }
        f.append('<div class="ui-dialog-content ui-widget-content" style="height: auto;"><iframe style="border:0 none" frameborder="0"/></div>');
        f.appendTo(document.body);
        var c = f.find("iframe"), g = e.url.indexOf("?") === -1 ? "?" : "&", b = e.url + g + "pfdlgcid=" + e.pfdlgcid, a = e.options.contentWidth || 640;
        c.width(a);
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
                    }, modal: e.options.modal, resizable: e.options.resizable, draggable: e.options.draggable, width: e.options.width, height: e.options.height})
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