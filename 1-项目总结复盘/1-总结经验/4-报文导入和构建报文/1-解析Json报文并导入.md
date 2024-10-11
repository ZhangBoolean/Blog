举例报文内容如下：

    [
    {"EBELN":"5700006029","INCO1":"DAP"},
    {"EBELN":"5700006030","INCO1":"DAT"},
    {"EBELN":"5700006031","INCO1":"FCA"}
    ]

通过如下方法即可把数据类型的内容，赋值到字符串上

                String json = sfJsonApiVo.getJson();
                List<Map> list  = JSON.parseArray(json,Map.class);
                List<TmIncotermsData> tmIncotermsDataList = new ArrayList<>();
                for (int n = 0;list.size()>n;n++){
                    Map map = list.get(n);
                    String saId = (String) map.get("EBELN");
                    String incoterms = (String) map.get("INCO1");
