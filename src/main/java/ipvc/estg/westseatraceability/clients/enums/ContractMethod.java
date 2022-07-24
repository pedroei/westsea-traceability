package ipvc.estg.westseatraceability.clients.enums;

public enum ContractMethod {
    CREATE_PRODUCT_LOT("WestseaTraceShipContract:CreateProductLot"),
    CREATE_ACTIVITY("WestseaTraceShipContract:CreateActivity"),
    READ_ALL_PRODUCT_LOTS("WestseaTraceShipContract:GetAllProductLot"),
    READ_PRODUCT_LOT("WestseaTraceShipContract:ReadProductLot"),
    READ_ALL_ACTIVITIES("WestseaTraceShipContract:GetAllActivities"),
    GET_TRACEABILITY("WestseaTraceShipContract:GetTraceabilityByReferenceNum"),
    UPDATE_PRODUCT_LOT_DOCUMENT_KEYS("WestseaTraceShipContract:UpdateProductLotDocumentKeys");


    public final String value;

    ContractMethod(String value) {
        this.value = value;
    }
}
