package co.com.hbt.koba.objeto;

import org.openqa.selenium.By;

import co.com.hbt.koba.util.ActionsUtil;

/**
 * 
 * Clase responsable almacenar los xphat a usar
 *
 * @uthor hataborda <br>
 *        Harold Taborda <br>
 *        hataborda@heinsohn.com.co
 *
 * @date 16/01/2020
 * @version 1.0
 */
public class ObjetosPlmLogin {
    
    /**
     * Constructor
     */
    public ObjetosPlmLogin() {
        ActionsUtil.objetosPut("username", By.name("user"));
        ActionsUtil.objetosPut("password", By.name("pass"));
        ActionsUtil.objetosPut("iniciarsesion", By.id("login_button"));
        ActionsUtil.objetosPut("navegacion", By.cssSelector(".nc-search-form-wrapper"));
        ActionsUtil.objetosPut("nombredelacuentadelcliente", By.xpath("//*[@data-attr-id='-1']/..//input"));
        ActionsUtil.objetosPut("busqueda", By.xpath("//*[@tabindex='0']"));
        ActionsUtil.objetosPut("productos", By.xpath("//*[@accesskey='p']"));
        ActionsUtil.objetosPut("iframeroe", By.name("iframe_roe"));
        ActionsUtil.objetosPut("check", By.xpath("(//td/input[1])[@type='checkbox']"));
        ActionsUtil.objetosPut("equipos", By.xpath("//*[@type='checkbox' and @tabindex='-1']/..//input"));
        ActionsUtil.objetosPut("modelo", By.xpath("//*[@class='search-filter__type-input']/..//input"));
        ActionsUtil.objetosPut("agregar", By.xpath("//a[@class='roe-table-cell add']"));
        ActionsUtil.objetosPut("ok", By.cssSelector(".ok-button"));
        ActionsUtil.objetosPut("precio", By.cssSelector(".priceRenderer"));
        ActionsUtil.objetosPut("error", By.cssSelector(".hw-search-warning"));
        ActionsUtil.objetosPut("serviciodebusqueda",
                By.xpath("//*[@class='search-filter__search-input__overview']/..//input"));
        ActionsUtil.objetosPut("plan", By.name("233"));
        ActionsUtil.objetosPut("planfamiliar", By.name("235"));
        ActionsUtil.objetosPut("prepagotodoenuno", By.xpath("//input[@type='checkbox' and @tabindex='-1']"));
        ActionsUtil.objetosPut("miplancontrolfamiliar", By.xpath("//input[@type='checkbox' and @tabindex='-1']"));
        ActionsUtil.objetosPut("pagoencuotas", By.xpath("//td/input[2][@class='checkbox']"));
        ActionsUtil.objetosPut("combocuotas",
                By.xpath("//div[@class='characteristicValues characteristic-invalid']//div//select"));
        ActionsUtil.objetosPut("tablagestionordencliente", By.xpath("//tbody[@id='grp_1body']/tr/td"));
        ActionsUtil.objetosPut("tabladatosfinales",           By.xpath("//tr[@class='gwt-row nc-row-rolled-down']/td"));
        ActionsUtil.objetosPut("tablainformaciongestionventas",
                By.xpath("//tbody[@id='grp_9134228558813175736body']//tr//td"));

        
        /** ssp*/

        ActionsUtil.objetosPut("tablanuevacuentafacturacionpospago",
                By.xpath("//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-front JQPopup ui-draggable']//tbody/tr/td"));
        ActionsUtil.objetosPut("selectgenericocliente",
                By.xpath(".//select[@class='gwt-ListBox nc-field-list-value nc-field-container']"));

        
//  SSPCONTADO
        ActionsUtil.objetosPut("tiendamovistar",            By.xpath("//div[@class='nc-header__user-bar-image nc-header__user-bar_shop']"));

        ActionsUtil.objetosPut("mimovistar",            By.xpath("//div[@class='nc-header__user-bar-image nc-header__user-bar_enter']"));
        ActionsUtil.objetosPut("campocedula",           By.xpath("//input[@name='documentId']"));
        ActionsUtil.objetosPut("continuar",             By.xpath("//button[@class='nc-button taButton nc-button_width_l nc-button_type_primary jsButtonSubmit']"));
        ActionsUtil.objetosPut("campocodigo",           By.xpath("//input[@name='oneTimePassword']"));
        ActionsUtil.objetosPut("iniciarsesionssp",      By.xpath("//button[@class='nc-button taButton nc-button_type_primary nc-button_width_l jsButtonSubmitOneTimePassword']"));
        ActionsUtil.objetosPut("busquedaplan",          By.xpath("//input[@placeholder='¿Qué plan está buscando?']"));
        ActionsUtil.objetosPut("busquedaequipointerno", By.xpath("//input[@placeholder='Buscar']"));
        ActionsUtil.objetosPut("enter",                 By.xpath("//i[@class='nc-form-search__submit-icon']")); 
        ActionsUtil.objetosPut("loquiero",              By.xpath("//a[@class='nc-button taButton nc-button_width_xs jsSelectDeviceButton nc-device__select-action-button']"));
        ActionsUtil.objetosPut("comprarplanconcelular", By.xpath("//button[@class='nc-button taButton nc-button_type_primary jsBuyPlanWithDevice']"));  
        ActionsUtil.objetosPut("loquieromodelo",        By.xpath("//a[@class='nc-button taButton nc-button_width_xs jsSelectDeviceButton nc-device__select-action-button'] ")); 
        ActionsUtil.objetosPut("comprar",               By.xpath("//div[@class='nc-device-price-characteristics__section']//div[1]//div[2]//button[1]//i[1]"));
        ActionsUtil.objetosPut("preciocontadocuotassp", By.xpath("//div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[2]/div[2]/div[3]/div/span[1]"));
        ActionsUtil.objetosPut("preciocontadossp",      By.xpath("//div[@class='nc-order-summary__content-data nc-order-summary__content-data-container']//span[@class='nc-order-summary__price-val'][contains(text(),'57,00')]"));
        //ActionsUtil.objetosPut("combocuotassp",       By.xpath("//span[@class='ui-selectmenu-text']"));
        ActionsUtil.objetosPut("combocuotasspbtn",      By.xpath("//span[@id='ui-id-1-button']"));
        ActionsUtil.objetosPut("combocuotasspbtn1cuota",        By.xpath("//*[@id='ui-id-8-button']"));
        ActionsUtil.objetosPut("combocuotasspbtn3cuota",        By.xpath("//*[@id='ui-id-15-button']"));    
        ActionsUtil.objetosPut("combocuotasspsincuotas",       By.xpath("//*[@id='ui-id-7']"));
        ActionsUtil.objetosPut("combocuotassp1cuotas",         By.xpath("//*[@id='ui-id-12']"));
        ActionsUtil.objetosPut("combocuotassp3cuotas",         By.xpath("//*[@id='ui-id-20']"));
        ActionsUtil.objetosPut("combocuotassp18cuotas",         By.xpath("//li[@id='ui-id-2']"));
        //*[@id="ui-id-5"]
        ActionsUtil.objetosPut("buscarmodelo",          By.xpath("//input[@placeholder='Buscar']"));
        ActionsUtil.objetosPut("loquieromodelorenovacion",  By.xpath("//div'nc-device-catalog__list jsDeviceCatalogList']//div/div/div/div/a"));    
        ActionsUtil.objetosPut("tienda",  By.xpath("//a[@class='nc-header__navigation-link _not-mobile']"));
        ActionsUtil.objetosPut("quierounplan",  By.xpath("//div[contains(@class,'nc-dropdown__content')]//a[@class='nc-shop-menu__item nc-shop-menu__item_plans']"));
        ActionsUtil.objetosPut("planes",  By.xpath("//div[@class='nc-plan__content']"));
        ActionsUtil.objetosPut("planloquiero",  By.xpath(".//a[@class='nc-button taButton nc-button_type_primary nc-button_width_wide jsSelectPlanButton']"));
        ActionsUtil.objetosPut("imeiexterno",  By.xpath("//input[@name='imei']"));
        ActionsUtil.objetosPut("continuarcarrito",  By.xpath("//i[@class='nc-button__icon _cart-active']"));
        ActionsUtil.objetosPut("continuarventa",  By.xpath("//button[@class='nc-button taButton nc-button_type_primary nc-button_width_l jsProceedShoppingCartButton']"));
        ActionsUtil.objetosPut("procederpago",  By.xpath("//button[@class='nc-button taButton jsTooltip nc-button_type_primary jsProvidePaymentDetails']"));
        ActionsUtil.objetosPut("comprarsoloplan",  By.xpath("//button[@class='nc-button taButton nc-button_type_button jsBuyPlanOnly']"));
        ActionsUtil.objetosPut("continuarimei",  By.xpath("//button[@class='nc-button taButton nc-button_type_cart-active jsAddToCartButton']"));
        ActionsUtil.objetosPut("validarserieimei",  By.xpath("//button[@class='nc-button taButton nc-button_width_m nc-button_type_primary jsCheckImeiButton']"));
        ActionsUtil.objetosPut("jornadaenvio",  By.xpath("//span[@class='ui-icon ui-icon-triangle-1-s']"));
        ActionsUtil.objetosPut("jornadamaniana",  By.xpath("//li[contains(text(),'Mañana')]"));
        ActionsUtil.objetosPut("jornadatarde",  By.xpath("//li[contains(text(),'Tarde')]"));
        ActionsUtil.objetosPut("horarioentrega",  By.xpath("//input[@class='nc-form-text__input taEditElement jsFormDate jsDate']"));
        ActionsUtil.objetosPut("telefonocontacto",  By.xpath("//div[@class='nc-delivery-details__col_phone_fields jsContactsDeliveryPhone']//div[@class='nc-form-field taParam jsFormField']//div[@class='nc-form-text ui-front taTextEdit taValue']//input"));
      //
      //

      
        ActionsUtil.objetosPut("tablaequipointerno", By.xpath(
                "//div[@class='roe-widget-area selected-hardware-offers']//tr[@class='characteristic-item force_enabled']/td"));
        ActionsUtil.objetosPut("inputgenerico", By.xpath(".//input[@class='refsel_input']"));
        ActionsUtil.objetosPut("selectgenerico", By.xpath(".//select"));
        ActionsUtil.objetosPut("cuotainicial",
                By.xpath("//table[@class='parameters']//tr[@class='characteristic-item force_enabled']//input"));
        ActionsUtil.objetosPut("tablaclienteempresarial",
                By.xpath("//td[@class='nc-composite-layout-item-horizontal nc-new-customer-parctrl']//tr[@class='gwt-row nc-row-rolled-down']/td"));
        ActionsUtil.objetosPut("tablaclienteempresarialultimafila",
                By.xpath("//td[@class='nc-composite-layout-item-horizontal nc-new-customer-parctrl']//tr[@class='gwt-row nc-row-rolled-down gwt-bottom-line']/td"));
        ActionsUtil.objetosPut("primerresultadoclienteempresarial",
                By.xpath("//div[@id='nc_refsel_list_row_0']"));
        ActionsUtil.objetosPut("inputtypetext",
                By.xpath(".//input[@type='text']"));
        ActionsUtil.objetosPut("botonmascontactoprimario",
                By.xpath(".//img[@class='gwt-Image add-button nc-reference-selector-add-button']"));
        ActionsUtil.objetosPut("tablaclienteempresarialcontactoprincipal",
                By.xpath("//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-front JQPopup']//tr[@class='gwt-row nc-row-rolled-down']/td"));
        ActionsUtil.objetosPut("botonmastabla",
                By.xpath(".//img[@class='gwt-Image nc-field-add-button']"));
        ActionsUtil.objetosPut("panelizquierdo",
                By.xpath("//button[@class='gwt-Button left-panel-collapse-button collapsed' "
                        + "or @class='left-panel-collapse-button collapsed gwt-Button' "
                        + "or @class='collapsed left-panel-collapse-button gwt-Button']"));
        ActionsUtil.objetosPut("panelizquierdoresumen",
                By.xpath("//div[@title=' Resumen']"));
        ActionsUtil.objetosPut("panelizquierdoentradaorden",
                By.xpath("//div[@title='Entrada de orden']"));
        ActionsUtil.objetosPut("tablainferiorcuentafacturacionpago",
                By.xpath("//div[@class='ui-widget-overlay-under-wrapper']//tr[@class='gwt-row nc-row-rolled-down gwt-last-row']/td"));
        ActionsUtil.objetosPut("tablasuperiorcuentafacturacionpago",
                By.xpath("//div[@class='ui-widget-overlay-under-wrapper']//tr[@class='gwt-row nc-row-rolled-down']/td"));
        ActionsUtil.objetosPut("textreadonly",
                By.xpath(".//div[@class=\"nc-field-text-readonly\"]"));
        ActionsUtil.objetosPut("recalcular",
                By.xpath("//div[@class='text_box roe-warning roe-warning-major server-item']//*[@class='roe_link_text'][contains(text(),'Recalcular')]"));

    }
}

