package ipvc.estg.westseatraceability.utils;

public class MockConstants {

    public static final String MOCK_LIST_PRODUCT_LOT = """
            [
                {
                    "docType": "productLot",
                    "referenceNumber": "87265223",
                    "isSerialNumber": false,
                    "designation": "Second Product",
                    "productType": "part",
                    "initialQuantity": 100.0,
                    "availableQuantity": 50.0,
                    "documentKeys": [
                        "key4",
                        "key5"
                    ],
                    "ID": "7b19ac3d-175e-40b0-a0b5-9e7c31192b10"
                },
                {
                    "docType": "productLot",
                    "referenceNumber": "515555",
                    "isSerialNumber": false,
                    "designation": "Third Product",
                    "productType": "component",
                    "initialQuantity": 500.0,
                    "availableQuantity": 500.0,
                    "documentKeys": [
                        "key11",
                        "key21"
                    ],
                    "ID": "84c23e2a-be2d-4c16-b348-56426792bdbd"
                },
                {
                    "docType": "productLot",
                    "referenceNumber": "5798634869",
                    "isSerialNumber": false,
                    "designation": "First Product",
                    "productType": "part",
                    "initialQuantity": 500.0,
                    "availableQuantity": 390.0,
                    "documentKeys": [
                        "key1",
                        "key2",
                        "key33"
                    ],
                    "ID": "cb31500e-8e58-4ab3-9543-7026948d0165"
                }
            ]""";

    public static final String MOCK_LIST_ACTIVITY = """
            [
                {
                    "docType": "activity",
                    "designation": "CUT",
                    "userId": "62509ccf2ccbe66dc73f2a97",
                    "inputProductLots": {
                        "cb31500e-8e58-4ab3-9543-7026948d0165": 10.0
                    },
                    "outputProductLot": {
                        "docType": "productLot",
                        "referenceNumber": "87265223",
                        "isSerialNumber": false,
                        "designation": "Second Product",
                        "productType": "part",
                        "initialQuantity": 100.0,
                        "availableQuantity": 100.0,
                        "documentKeys": [
                            "key4",
                            "key5"
                        ],
                        "ID": "7b19ac3d-175e-40b0-a0b5-9e7c31192b10"
                    },
                    "ID": "a14f0dbc-3284-49e9-85b0-5a5e87d64da5",
                    "dateTime": "2022-05-08T11:44:12Z"
                },
                {
                    "docType": "activity",
                    "designation": "Fusion",
                    "userId": "62509ccf2ccbe66dc73f2a97",
                    "inputProductLots": {
                        "7b19ac3d-175e-40b0-a0b5-9e7c31192b10": 50.0,
                        "cb31500e-8e58-4ab3-9543-7026948d0165": 100.0
                    },
                    "outputProductLot": {
                        "docType": "productLot",
                        "referenceNumber": "515555",
                        "isSerialNumber": false,
                        "designation": "Third Product",
                        "productType": "component",
                        "initialQuantity": 500.0,
                        "availableQuantity": 500.0,
                        "documentKeys": [
                            "key11",
                            "key21"
                        ],
                        "ID": "84c23e2a-be2d-4c16-b348-56426792bdbd"
                    },
                    "ID": "f00048c2-a90c-4f6f-8293-bc1856cebd1a",
                    "dateTime": "2022-05-08T11:46:00Z"
                }
            ]
            """;

    public static final String MOCK_PRODUCT_LOT_TRACEABILITY = """
            {
                "referenceNumber": "515555",
                "isSerialNumber": false,
                "designation": "Third Product",
                "productType": "component",
                "initialQuantity": 500.0,
                "availableQuantity": 500.0,
                "usedQuantityAsInput": null,
                "documentKeys": [
                    "key11",
                    "key21"
                ],
                "activity": {
                    "designation": "Fusion",
                    "userId": "62509ccf2ccbe66dc73f2a97",
                    "inputProductLots": [
                        {
                            "referenceNumber": "87265223",
                            "isSerialNumber": false,
                            "designation": "Second Product",
                            "productType": "part",
                            "initialQuantity": 100.0,
                            "availableQuantity": 50.0,
                            "usedQuantityAsInput": 50.0,
                            "documentKeys": [
                                "key4",
                                "key5"
                            ],
                            "activity": {
                                "designation": "CUT",
                                "userId": "62509ccf2ccbe66dc73f2a97",
                                "inputProductLots": [
                                    {
                                        "referenceNumber": "5798634869",
                                        "isSerialNumber": false,
                                        "designation": "First Product",
                                        "productType": "part",
                                        "initialQuantity": 500.0,
                                        "availableQuantity": 390.0,
                                        "usedQuantityAsInput": 10.0,
                                        "documentKeys": [
                                            "key1",
                                            "key2",
                                            "key33"
                                        ],
                                        "activity": null,
                                        "ID": "cb31500e-8e58-4ab3-9543-7026948d0165"
                                    }
                                ],
                                "ID": "a14f0dbc-3284-49e9-85b0-5a5e87d64da5",
                                "dateTime": "2022-05-08T11:44:12Z"
                            },
                            "ID": "7b19ac3d-175e-40b0-a0b5-9e7c31192b10"
                        },
                        {
                            "referenceNumber": "5798634869",
                            "isSerialNumber": false,
                            "designation": "First Product",
                            "productType": "part",
                            "initialQuantity": 500.0,
                            "availableQuantity": 390.0,
                            "usedQuantityAsInput": 100.0,
                            "documentKeys": [
                                "key1",
                                "key2",
                                "key33"
                            ],
                            "activity": null,
                            "ID": "cb31500e-8e58-4ab3-9543-7026948d0165"
                        }
                    ],
                    "ID": "f00048c2-a90c-4f6f-8293-bc1856cebd1a",
                    "dateTime": "2022-05-08T11:46:00Z"
                },
                "ID": "84c23e2a-be2d-4c16-b348-56426792bdbd"
            }
            """;
}
