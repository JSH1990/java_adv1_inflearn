import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public int insertScIPBPartinfo(RegisterDto regDto, NodeList ipbList, int ipbType) {
		logger.info("SearchRegister.insertScIPBPartinfo 중 검색 내용 중 IPB(Partinfo) 관련 XCont(XML Data) DB 등록 실행");
		
		int rtInt = 0;		//등록할 검색용 IPB 부대급 데이터가 없을 수도 있기에 0으로 초기화
		
		if (ipbList == null || ipbList.getLength() == 0 || regDto == null) {
			logger.info("SearchRegister.insertScIPBPartinfo 중 검색 내용 중 IPB(Partinfo) 관련 XCont(XML Data) DB 등록  취소 됨...............");
			return rtInt;
		}
		
		try {
			ArrayList<SearchPartinfoDto> insertList = new ArrayList<SearchPartinfoDto>(); //insertList - IPBPartinfo를 저장할 리스트
			int insertCnt		= 0; //저장된 갯수
			
			String ipbId		= "";
			String indexNo		= "";
			String grphNo		= "";
			String ipbCode		= "";
			String partNo		= "";
			String partName		= "";
			String nsn			= "";
			String cage			= "";
			String rdn			= "";
			String upa			= "";
			String smr			= "";
			String stdMngt		= "";
			String wuc			= "";				//2023.12.07 - 작업단위부호 추가 - jingi.kim
			
			for (int loop=0; loop<ipbList.getLength(); loop++) {
				Node ipbNode = ipbList.item(loop);
				NamedNodeMap ipbAttr = ipbNode.getAttributes();
				
				if (ipbNode == null || !ipbNode.getNodeName().equals(DTD.IPB_PARTINFO)) {
					continue;
				}
				
				ipbId		= XmlDomParser.getAttributes(ipbAttr, ATTR.ID);
				indexNo		= XmlDomParser.getAttributes(ipbAttr, ATTR.IPB_INDEXNUM);
				//2022 06 27 Park.J.S. Update
				if(!"KTA".equalsIgnoreCase(ext.getBIZ_CODE())){
					grphNo	= XmlDomParser.getAttributes(ipbAttr, ATTR.IPB_GRPHNUM);
				}else {
					grphNo	= XmlDomParser.getAttributes(ipbAttr, ATTR.IPB_GRPHNUM2);
				}
				ipbCode		= XmlDomParser.getAttributes(ipbAttr, ATTR.IPB_IPBCODE);
				rdn			= XmlDomParser.getAttributes(ipbAttr, ATTR.IPB_RDN);
				upa			= XmlDomParser.getAttributes(ipbAttr, ATTR.IPB_UNITSPER);
				stdMngt		= XmlDomParser.getAttributes(ipbAttr, ATTR.IPB_STD);
				wuc			= XmlDomParser.getAttributes(ipbAttr, ATTR.IPB_WUC);			//2023.12.07 - 작업단위부호 추가 - jingi.kim
				
				Node pbaseNode = XmlDomParser.getNodeFromXPathAPI(ipbNode, "./child::partbase");
				if (pbaseNode != null && pbaseNode.getNodeName().equals(DTD.IPB_PARTBASE)) {
					NamedNodeMap pbaseAttr = pbaseNode.getAttributes();
					partNo		= XmlDomParser.getAttributes(pbaseAttr, ATTR.IPB_PARTNUM);
					partName	= XmlDomParser.getAttributes(pbaseAttr, ATTR.NAME);
					nsn			= XmlDomParser.getAttributes(pbaseAttr, ATTR.IPB_NSN);
					cage			= XmlDomParser.getAttributes(pbaseAttr, ATTR.IPB_CAGE);
					smr			= XmlDomParser.getAttributes(pbaseAttr, ATTR.IPB_SMR);
					if (partName.equals("")) {
						partName = XmlDomParser.getTxt(pbaseNode);
					}
				}
				
				if (ipbId.equals("")) {
					logger.info("SearchRegister.insertScIPBPartinfo Contents is NULL ipbId");
					continue;
				}
				//부대IPB는 품목번호(indexnum) 0이면 검색 테이블에 미등록, 야전IPB는 품목번호 0이어도 등록
				if (ipbType == IConstants.IPB_TYPE_UNIT && indexNo.equals("")) {
					logger.info("SearchRegister.insertScIPBPartinfo IPB Unit & index_no is NULL ipbType");
					continue;
				}
				
				//
				String parentTocoId = "";
				if (ipbType == IConstants.IPB_TYPE_FIELD && ipbNode.getParentNode() != null) {
					NamedNodeMap paAttr = ipbNode.getParentNode().getAttributes();
					parentTocoId = XmlDomParser.getAttributes(paAttr, ATTR.ID);
				}
				//2021 08 13 parkjs 부대급도 목차_id로 설정 안할경우 조회가 안됨
				if (ipbType == IConstants.IPB_TYPE_UNIT && ipbNode.getParentNode() != null) {
					NamedNodeMap paAttr = ipbNode.getParentNode().getAttributes();
					parentTocoId = XmlDomParser.getAttributes(paAttr, ATTR.ID);
				}
				
					
				SearchPartinfoDto scDto = new SearchPartinfoDto();
				scDto.setToKey(regDto.getToKey());
				scDto.setTocoId(ipbId);		//IPB TO는 ipb_id가 목차_id가 됨
				
				//20200319 edit LYM 야전IPB일 경우는, 목차_id로 지정
				logger.info("ipbType : "+ipbType+", IConstants.IPB_TYPE_FIELD : "+IConstants.IPB_TYPE_FIELD);
				if (ipbType == IConstants.IPB_TYPE_FIELD) {
					scDto.setTocoId(parentTocoId);
				}
				//2021 08 13 parkjs 부대급도 목차_id로 설정 안할경우 조회가 안됨
				if (ipbType == IConstants.IPB_TYPE_UNIT) {
					logger.info("2021 08 13 parkjs 부대급도 목차_id로 설정  안할경우 조회가 안됨 : "+parentTocoId);
					scDto.setTocoId(parentTocoId);
				}
				scDto.setIndexNo(indexNo);
				scDto.setGrphNo(grphNo);
				scDto.setIpbCode(ipbCode);
				scDto.setPartNo(partNo);
				scDto.setPartName(partName);
				scDto.setNsn(nsn);
				scDto.setCage(cage);
				scDto.setRdn(rdn);
				scDto.setUpa(upa);
				scDto.setSmr(smr);
				scDto.setStdMngt(stdMngt);
				scDto.setCreateUserId(regDto.getUserId());
				//2022 03 18 건바이건 등록하도록 수정
				//System.out.println("insertList1 : "+scDto.getTocoId()+", "+scDto.getPartNo()+", "+scDto.getPartName()+", "+scDto.getNsn()+", "+scDto.getRdn());
				//rtInt = scPartMapper.insertAllDaoMDB(scDto);
				scDto.setWuc(wuc); 			//2023.12.07 - 작업단위부호 추가 - jingi.kim
				insertList.add(scDto);
				
				insertCnt++;
				
				if (insertList.size() >= IConstants.REG_MAX_LIST_LARGE) {
					if(regDto.getDbType().equalsIgnoreCase("mdb")) {
						for(int j=0; j<insertList.size(); j++) {
							rtInt = scPartMapper.insertAllDaoMDB(insertList.get(j));
						}
					} else {
						//2022 04 07 속도 이슈로 인해 최대치 넘어갈경우 분할 처리하도록 수정
						if(insertList.size() >= IConstants.REG_MAXIMUM){
							for(int j=0; j<insertList.size(); j = j+IConstants.REG_MAXIMUM) {
								ArrayList<SearchPartinfoDto> tempInsertList = new ArrayList<SearchPartinfoDto>();
								if(j+IConstants.REG_MAXIMUM < insertList.size()) {
									tempInsertList.addAll(insertList.subList(j, j+IConstants.REG_MAXIMUM));
								}else{
									tempInsertList.addAll(insertList.subList(j, insertList.size()));
								}
								rtInt = scPartMapper.insertAllDao(tempInsertList);
							}
						}else {
							rtInt = scPartMapper.insertAllDao(insertList);
						}
						//rtInt = scPartMapper.insertAllDao(insertList);
					}
					insertList = new ArrayList<SearchPartinfoDto>();
					if (rtInt <= 0) { break; }
				}
				
			}
			
			if (insertList.size() > 0) {
				if(regDto.getDbType().equalsIgnoreCase("mdb")) {
					for(int j=0; j<insertList.size(); j++) {
						rtInt = scPartMapper.insertAllDaoMDB(insertList.get(j));
					}
				} else {
					rtInt = scPartMapper.insertAllDao(insertList);
				}
				insertList = null;
			}
			
			logger.info("SearchRegister.insertScIPBPartinfo insertList.size():"+insertCnt+", rtInt:"+rtInt);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("SearchRegister.insertScIPBPartinfo Exception : "+regDto.getToKey(),ex);
			return -1;
		}
		
		return rtInt;
	}
