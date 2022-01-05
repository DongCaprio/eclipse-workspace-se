package com.openminds.edms.dc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.edms.common.EDMSUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openminds.com.struts2.ComAction;
import com.openminds.com.util.DhxHtmlGridUtil;
import com.openminds.com.util.MsgException;
import com.openminds.com.util.RichList;
import com.openminds.com.util.xml.DhxGirdExcelXmlUtil;
import com.openminds.com.util.xml.DhxGirdXmlUtil;
import com.openminds.edms.bs.CodeService;
import com.openminds.edms.dto.CheckOutDTO;
import com.openminds.edms.dto.ComDTO;
import com.openminds.edms.dto.DocumentDTO;
import com.openminds.edms.dto.GridColumnsDTO;
import com.openminds.edms.dto.OutSourcingDTO;
import com.openminds.edms.dto.SessionDTO;
import com.openminds.edms.dto.TreeDTO;
import com.openminds.edms.dto.VMDTO;
import com.openminds.edms.dto.WorkflowDetailDTO;
import com.openminds.edms.personalization.PersonalizationDTO;
import com.openminds.edms.personalization.PersonalizationService;
import com.openminds.edms.site.hanwha.dc.DistributeMngDTO;
import com.openminds.edms.wf.WorkFlowFormDTO;
import com.openminds.edms.wf.WorkFlowService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 도면 조회와 관련된 Action Class 입니다.
 * 
 * @author 김태성(bkmilk)
 * @since 2011/04/11
 * @version 124
 */
@Namespace("/edms/dc")
@ResultPath(value = "/")
public class SearchDocAction extends ComAction {

	private static final long serialVersionUID = 1L;

	private CheckOutDTO checkOutDTO = null;
	private TreeDTO treeDTO = null;
		
	@Autowired
	CodeService codeService;
	
	@Autowired
	WorkFlowService workFlowService;
	
	@Autowired
	PersonalizationService personalizationService;
	
	private WorkFlowFormDTO workFlowFormDTO;
	private OutSourcingDTO outSourcingDTO = null;
	private DistributeMngDTO distributeMngDTO;

	private LinkedHashMap<String, String> companylist;
	private LinkedHashMap<String, String> extlist;
	private LinkedHashMap<String, String> gradecodelist;
	private LinkedHashMap<String, String> maintcodelist;
	private LinkedHashMap<String, String> printcodelist;
	private LinkedHashMap<String, String> extcollist;
	private LinkedHashMap<String, String> comcodelist;		// 2014.06.03 WooChul Jung Add Com_code list
	private LinkedHashMap<String, String> ruleNoList;		// 2014.11.29
	private LinkedHashMap<String, String> Company_List; //외주업체 리스트
	private LinkedHashMap<String, String> shared_List;
	private LinkedHashMap<String, String> categoryMap;
	
	private String default_date;
	private LinkedHashMap<String, String> outlist;
	private SearchDocFormDTO searchFormDTO;
	private LinkedHashMap<String, String> sizelist;
	private LinkedHashMap<String, String> typecodelist;
	private File[] file;
    private String[] fileFileName;
    private String token;
    private String doc_seq = null ;
    private String gridNames = null ;
    private String file_id = null ;    
    private String doc_seqs = null ;
	private String treeRoot = "";
	private String kindRoot ="";
	private String excel_form = null;
	private DocumentDTO documentDTO;
	
	
	public DocumentDTO getDocumentDTO() {
		return documentDTO;
	}
	public void setDocumentDTO(DocumentDTO documentDTO) {
		this.documentDTO = documentDTO;
	}
	public LinkedHashMap<String, String> getCategoryMap() {
		return categoryMap;
	}
	public void setCategoryMap(LinkedHashMap<String, String> categoryMap) {
		this.categoryMap = categoryMap;
	}
	public String getExcel_form() {
		return excel_form;
	}
	public void setExcel_form(String excel_form) {
		this.excel_form = excel_form;
	}
	public LinkedHashMap<String, String> getShared_List() {
		return shared_List;
	}
	public void setShared_List(LinkedHashMap<String, String> shared_List) {
		this.shared_List = shared_List;
	}

	@Autowired
	SearchDocService searchService;
	@SuppressWarnings("rawtypes")

	//add_code 20161213 보안작업 변경
	public File[] getFile() {
		File[] newFile = new File[file.length];
		System.arraycopy(file, 0, newFile, 0, file.length);
		return newFile;
	}
	//end

	//add_code 20161213 보안작업 변경
	public void setFile(File[] file) {
		File[] newFile = new File[file.length];
	    System.arraycopy(file, 0, newFile, 0, file.length);
	    this.file = newFile;
	}
	//end
	
	//add_code 20161213 보안작업 변경
	public String[] getFileFileName() {
		String[] newfileFileName = new String[fileFileName.length];
		System.arraycopy(fileFileName, 0, newfileFileName, 0, fileFileName.length);
		return newfileFileName;
	}
	//end	

	//add_code 20161213 보안작업 변경
	public void setFileFileName(String[] fileFileName) {
	    String[] newfileFileName = new String[fileFileName.length];
	    System.arraycopy(fileFileName, 0, newfileFileName, 0, fileFileName.length);
	    this.fileFileName = newfileFileName;
	}
	//end

	@Override
	protected void afterSetParameters(Map<String, String[]> map) {	}

	
	@Action(value = "initCompHis", results = { @Result(name = "success", location = "/std/dc/CompHis.jsp") })
	public String initCompHis() {
		log.debug(" Action : initCompHis");
		return SUCCESS;
	}
	
	
	/***
	 * 조회 페이지를 초기값 설정과 로드합니다.
	 * 
	 * @author 김태성(bkmilk)
	 * @since 2011/04/11
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "initSearchPage", results = { @Result(name = "success", location = "/std/dc/searchDoc.jsp") })
	public String initPage() throws ParseException {
		log.debug("Start");
		//companylist = codeService.getColombs("COMPANY");
		sizelist = codeService.getColombs("SIZE");
		
		
		printcodelist = codeService.getColombs("PLOTCAUSE");
		extcollist = codeService.getColombs("EXTEND_COL");
		
		typecodelist = new LinkedHashMap<String, String>();
		typecodelist.put("", this.getText("W_ALL"));
		typecodelist.putAll(codeService.getColombs("DOCTYPE"));
		
		gradecodelist = new LinkedHashMap<String, String>();
		gradecodelist.put("", this.getText("W_ALL"));
		gradecodelist.putAll(codeService.getColombs("GRADE"));
		
		categoryMap = new LinkedHashMap<String, String>();
		categoryMap.put("", "");
		categoryMap.putAll(codeService.seCodeMap("BOOK_CATEGORY"));
		
		maintcodelist = new LinkedHashMap<String, String>();
		maintcodelist.put("", this.getText("W_ALL"));
		maintcodelist.putAll(codeService.getColombs("MAINT"));
		 
		extlist = new LinkedHashMap<String, String>();
		extlist.put("", this.getText("W_ALL"));
		extlist.putAll(searchService.seExts());
		
		/*	2014.06.03 WooChul Jung
		 * 		Add com code list for search
		*/		
		comcodelist = new LinkedHashMap<String, String>();
		comcodelist.put("", this.getText("W_ALL"));
		comcodelist.putAll(codeService.getColombs("COM_CODE"));

		Company_List = new LinkedHashMap<String, String>();
		Company_List.putAll(codeService.getColombs("COMPANY_LIST"));
		
		shared_List = searchService.seSharedList();
		
		
	/*	treeRoot = searchService.seTreeRoot();
		kindRoot = searchService.seKindRoot();*/
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		default_date = df.format(cal.getTime());
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "initSearchMyPage", results = { @Result(name = "success", location = "/std/dc/searchMyDoc.jsp") })
	public String initMyPage() throws ParseException {
		log.debug("Start");
		//companylist = codeService.getColombs("COMPANY");
		sizelist = codeService.getColombs("SIZE");
		
		
		printcodelist = codeService.getColombs("PLOTCAUSE");
		extcollist = codeService.getColombs("EXTEND_COL");
		
		typecodelist = new LinkedHashMap<String, String>();
		typecodelist.put("", this.getText("W_ALL"));
		typecodelist.putAll(codeService.getColombs("DOCTYPE"));
		
		gradecodelist = new LinkedHashMap<String, String>();
		gradecodelist.put("", this.getText("W_ALL"));
		gradecodelist.putAll(codeService.getColombs("GRADE"));
		
		
		maintcodelist = new LinkedHashMap<String, String>();
		maintcodelist.put("", this.getText("W_ALL"));
		maintcodelist.putAll(codeService.getColombs("MAINT"));
		 
		extlist = new LinkedHashMap<String, String>();
		extlist.put("", this.getText("W_ALL"));
		extlist.putAll(searchService.seExts());
		
		/*	2014.06.03 WooChul Jung
		 * 		Add com code list for search
		*/		
		comcodelist = new LinkedHashMap<String, String>();
		comcodelist.put("", this.getText("W_ALL"));
		comcodelist.putAll(codeService.getColombs("COM_CODE"));

		Company_List = new LinkedHashMap<String, String>();
		Company_List.putAll(codeService.getColombs("COMPANY_LIST"));
		
		shared_List = searchService.seSharedList();
		
		
	/*	treeRoot = searchService.seTreeRoot();
		kindRoot = searchService.seKindRoot();*/
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		default_date = df.format(cal.getTime());
		
		checkOutDTO = new CheckOutDTO();
		String checkoutpath = this.edmConfig.getConfig("CheckOutPath");
		checkOutDTO.setCheckout_path(checkoutpath);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "initSearchUpdatePage", results = { @Result(name = "success", location = "/std/dc/searchUpdateDoc.jsp") })
	public String initSearchUpdatePage() throws ParseException {
		log.debug("Start : initSearchUpdatePage");
		
		//companylist = codeService.getColombs("COMPANY");
		sizelist = codeService.getColombs("SIZE");
		
		
		printcodelist = codeService.getColombs("PLOTCAUSE");
		extcollist = codeService.getColombs("EXTEND_COL");
		
		typecodelist = new LinkedHashMap<String, String>();
		typecodelist.put("", this.getText("W_ALL"));
		typecodelist.putAll(codeService.getColombs("DOCTYPE"));
		
		gradecodelist = new LinkedHashMap<String, String>();
		gradecodelist.put("", this.getText("W_ALL"));
		gradecodelist.putAll(codeService.getColombs("GRADE"));
		
		
		maintcodelist = new LinkedHashMap<String, String>();
		maintcodelist.put("", this.getText("W_ALL"));
		maintcodelist.putAll(codeService.getColombs("MAINT"));
		 
		extlist = new LinkedHashMap<String, String>();
		extlist.put("", this.getText("W_ALL"));
		extlist.putAll(searchService.seExts());
		
		/*	2014.06.03 WooChul Jung
		 * 		Add com code list for search
		*/		
		comcodelist = new LinkedHashMap<String, String>();
		comcodelist.put("", this.getText("W_ALL"));
		comcodelist.putAll(codeService.getColombs("COM_CODE"));

		Company_List = new LinkedHashMap<String, String>();
		Company_List.putAll(codeService.getColombs("COMPANY_LIST"));
		
		shared_List = searchService.seSharedList();
		
		
	/*	treeRoot = searchService.seTreeRoot();
		kindRoot = searchService.seKindRoot();*/
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		default_date = df.format(cal.getTime());
		
		checkOutDTO = new CheckOutDTO();
		String checkoutpath = this.edmConfig.getConfig("CheckOutPath");
		checkOutDTO.setCheckout_path(checkoutpath);
		return SUCCESS;
	}
	
	
	@Action(value = "Select_del", results = { @Result(name = "success",  type= "text") })
	public String Select_del() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug(" Action : Select_del");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String doc_seq = request.getParameter("doc_seq");
		WorkflowDetailDTO WorkflowDeatailDTO = new WorkflowDetailDTO();
		WorkflowDeatailDTO.setDoc_seq(doc_seq);
		searchService.Select_del(WorkflowDeatailDTO);
		return SUCCESS;
	}
	
	//add20161124 나만의도면보기추가
	@Action(value = "AddView", results = { @Result(name = "success", type = "text") })
	public String AddView() {
		log.debug("Start");
		HttpServletRequest request = ServletActionContext.getRequest();
		String doc_seq = request.getParameter("doc_seq");
		String emp_id = request.getParameter("emp_id");	
		SearchDocFormDTO searchFormDTO = new SearchDocFormDTO();
		
		searchFormDTO.setDoc_seq(doc_seq);
		searchFormDTO.setEmp_id(emp_id);
		searchService.View_check(searchFormDTO);
		searchService.addView(searchFormDTO);
		this.xmlText = SUCCESS; 
		return SUCCESS;
	}
	//add20161206
	@Action(value = "sePrintCheck", results = { @Result(name = "success", type = "text") })
	public String sePrintCheck() {
		log.debug("Start sePrintCheck");
		HttpServletRequest request = ServletActionContext.getRequest();
		String doc_seq = request.getParameter("doc_seq");
		String emp_id = request.getParameter("emp_id");	
		SearchDocFormDTO searchFormDTO = new SearchDocFormDTO();
		
		searchFormDTO.setDoc_seq(doc_seq);
		searchFormDTO.setEmp_id(emp_id);
		String print_check = searchService.sePrintCheck(searchFormDTO);
		this.xmlText = print_check; 
		return SUCCESS;
	}
	@Action(value = "seViewCheck", results = { @Result(name = "success", type = "text") })
	public String seViewCheck() {
		log.debug("Start seViewCheck");
		HttpServletRequest request = ServletActionContext.getRequest();
		String doc_seq = request.getParameter("doc_seq");
		String emp_id = request.getParameter("emp_id");	
		SearchDocFormDTO searchFormDTO = new SearchDocFormDTO();
		
		searchFormDTO.setDoc_seq(doc_seq);
		searchFormDTO.setEmp_id(emp_id);
		String view_check = searchService.seViewCheck(searchFormDTO);
		this.xmlText = view_check; 
		return SUCCESS;
	}
	//end  
	@Action(value = "AddBag", results = { @Result(name = "success", type = "text") })
	public String AddBag() {
		log.debug("Start AddBag");
		HttpServletRequest request = ServletActionContext.getRequest();
		String doc_seq = request.getParameter("doc_seq");
		String emp_id = request.getParameter("emp_id");	
		SearchDocFormDTO searchFormDTO = new SearchDocFormDTO();
		
		searchFormDTO.setDoc_seq(doc_seq);
		searchFormDTO.setEmp_id(emp_id);
		searchService.doc_check(searchFormDTO);
		searchService.addBag(searchFormDTO);
		this.xmlText = SUCCESS; 
		return SUCCESS;
	}
	
	@Action(value = "SharedAddBag", results = { @Result(name = "success", type = "text") })
	public String SharedAddBag() {
		log.debug("Start SharedAddBag");
		searchService.SharedAddBag(searchFormDTO);
		this.xmlText = SUCCESS; 
		return SUCCESS;
	}
	
	
	
	@Action(value = "AddListSearch", results = { @Result(name = "success",  type= "girdxml") })
	public String AddListSearch() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("Start AddListSearch");

		if(this.posStart == -1)
		{
			seSearchCount("SC");
		}
		searchFormDTO.setPageValue(this.posStart,this.count);
		searchFormDTO.setOrderValue(this.orderby,this.direct);
		searchFormDTO.setFilterList(this.filterList);

		this.xmlData = DhxHtmlGridUtil.toXml(searchService.AddListSearch(searchFormDTO),searchFormDTO.getGridNames(),
				searchService.AddListSearchCnt(searchFormDTO),this.posStart);
		
		return SUCCESS;
	}
	
	@Action(value = "RemoveBag", results = { @Result(name = "success", type = "text") })
	public String RemoveBag() {
		log.debug("Start RemoveBag");
		HttpServletRequest request = ServletActionContext.getRequest();
		String doc_seq = request.getParameter("doc_seq");
		String emp_id = request.getParameter("emp_id");	
		SearchDocFormDTO searchFormDTO = new SearchDocFormDTO();
		
		searchFormDTO.setDoc_seq(doc_seq);
		searchFormDTO.setEmp_id(emp_id);
		searchService.doc_check(searchFormDTO);
		searchService.RemoveBag(searchFormDTO);
		this.xmlText = SUCCESS; 
		return SUCCESS;
	}


	public LinkedHashMap<String, String> getComcodelist() {
		return comcodelist;
	}

	public void setComcodelist(LinkedHashMap<String, String> comcodelist) {
		this.comcodelist = comcodelist;
	}

	/***
	 * 조회 결과 갯수를 구합니다. 결과 값을 Text로 받습니다.
	 * 
	 * @author 김태성(bkmilk)
	 * @since 2011/04/11
	 * @return
	 */
	@Action(value = "seCount", results = { @Result(name = "success", type = "text") })
	public String seCount() {
		log.debug("Start");
		this.xmlText = searchService.seCount(searchFormDTO)+"";
		return SUCCESS;
	}

	/***
	 * 조회 페이지를 초기값 설정과 로드합니다.
	 * 
	 * @author 김태성(bkmilk)
	 * @since 2011/04/11
	 * @return
	 * @throws org.springframework.batch.item.ParseException 
	 * @throws Exception 
	 * @throws UnexpectedInputException 
	 */
	@Action(value = "seSearch", results = { @Result(name = "success",  type= "girdxml") })
	public String seSearch() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("Start seSearch");
		log.debug(searchFormDTO.getDocumentDTO().getDoc_name());
		
		HttpServletRequest request = ServletActionContext.getRequest();
		searchFormDTO.setEmp_id(request.getParameter("emp_id"));
		
		if(this.posStart == -1)
		{
			seSearchCount("SC");
		}
		
		searchFormDTO.setPageValue(this.posStart,this.count);
		searchFormDTO.setOrderValue(this.orderby,this.direct);
		searchFormDTO.setFilterList(this.filterList);
		
//		if (!EDMSUtil.isNullOrEmptyString(this.orderby) && ("doc_no".equals(this.orderby))) {
//			this.orderby = "doc_no, rev_no" ;
//		}
		//add20161128
		if(searchFormDTO.getSearchString() != null && !"".equals(searchFormDTO.getSearchString())
				&& "block".equals(searchFormDTO.getAutoSaveYN())) {
			searchService.addSearchWordHis(searchFormDTO);
		}
		log.info("searchFormDTO.getGridNames() >>>> " + searchFormDTO.getGridNames());
		
		//end
//		searchFormDTO.setOrderValue(this.orderby,this.direct);
//		searchFormDTO.setFilterList(this.filterList);
		/*if(this.filterList.size()>0){
			log.debug(this.filterList);
			log.debug(searchFormDTO.getFilterList().toString());
		}*/
		
		@SuppressWarnings("unchecked")
		RichList<DocumentDTO> list = searchService.seSearch(searchFormDTO);
		
		int total_count = 0 ;
		if ((list == null) || (list.size() == 0)) {
			total_count = 0 ;
		} else {
			total_count = list.get(0).getSearch_total_count();
		}
		
		this.xmlData = DhxHtmlGridUtil.toXml(list,searchFormDTO.getGridNames(),
				total_count,this.posStart);
		
		return SUCCESS;
	}
	
	@Action(value = "seSearchMy", results = { @Result(name = "success", type = "girdxml") })
	public String seSearchMy() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("Start seSearch");
		log.debug(searchFormDTO.getDocumentDTO().getDoc_name());
		
		HttpServletRequest request = ServletActionContext.getRequest();
		searchFormDTO.setEmp_id(request.getParameter("emp_id"));
		if(this.posStart == -1)
		{
			seSearchCount("MC");
		}
		searchFormDTO.setPageValue(this.posStart, this.count);
		searchFormDTO.setOrderValue(this.orderby,this.direct);
		searchFormDTO.setFilterList(this.filterList);

		
		if(searchFormDTO.getSearchString() != null && !"".equals(searchFormDTO.getSearchString())
				&& "block".equals(searchFormDTO.getAutoSaveYN())) {
			searchService.addSearchWordHis(searchFormDTO);
		}

		// end
//		searchFormDTO.setOrderValue(this.orderby,this.direct);
//		searchFormDTO.setFilterList(this.filterList);
		/*
		 * if(this.filterList.size()>0){ log.debug(this.filterList);
		 * log.debug(searchFormDTO.getFilterList().toString()); }
		 */
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seSearchMy(searchFormDTO), searchFormDTO.getGridNames(),
				searchService.seCountMy(searchFormDTO), this.posStart);

		return SUCCESS;
	}
	
	@Action(value = "seSearchUpdate", results = { @Result(name = "success",  type= "girdxml") })
	public String seSearchUpdate() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("Start seSearchUpdate");
		log.debug(searchFormDTO.getDocumentDTO().getDoc_name());
		if(this.posStart == -1)
		{
			seSearchCount("SC");
		}
		searchFormDTO.setPageValue(this.posStart,this.count);
		searchFormDTO.setOrderValue(this.orderby,this.direct);
		searchFormDTO.setFilterList(this.filterList);
		
//		if (!EDMSUtil.isNullOrEmptyString(this.orderby) && ("doc_no".equals(this.orderby))) {
//			this.orderby = "doc_no, rev_no" ;
//		}

		//add20161128
		HttpServletRequest request = ServletActionContext.getRequest();
		searchFormDTO.setEmp_id(request.getParameter("emp_id"));

		
		//end
//		searchFormDTO.setOrderValue(this.orderby,this.direct);
//		searchFormDTO.setFilterList(this.filterList);
		/*if(this.filterList.size()>0){
			log.debug(this.filterList);
			log.debug(searchFormDTO.getFilterList().toString());
		}*/
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seSearchUpdate(searchFormDTO),searchFormDTO.getGridNames(),
				searchService.seCount2(searchFormDTO),this.posStart);
		
		return SUCCESS;
	}
	
	//add20161129 
	@Action(value = "seDocNoCheck", results = { @Result(name = "success",  type= "girdxml") })
	public String seDocNoCheck() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("Start seDocNoCheck");

		
		//add20161128
		this.xmlData = new DhxGirdXmlUtil(searchService.seDocNoCheck(searchFormDTO),searchFormDTO.getGridNames());
		
		return SUCCESS;
	}
	
	@Action(value = "seFavoriteDocSearch", results = { @Result(name = "success",  type= "girdxml") })
	public String seFavoriteDocSearch() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("Start seFavoriteDocSearch");

		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seFavoriteDocSearch(searchFormDTO),searchFormDTO.getGridNames(),
				searchService.seFavoriteDocSearchCount(searchFormDTO),this.posStart);
		
		return SUCCESS;
	}
	
	//공유폴더  조회
	@Action(value = "seShareDocSearch", results = { @Result(name = "success",  type= "girdxml") })
	public String seShareDocSearch() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("Start seShareDocSearch");
		if(this.posStart == -1)
		{
			seSearchCount("SC");
		}
		searchFormDTO.setPageValue(this.posStart,this.count);
		searchFormDTO.setOrderValue(this.orderby,this.direct);
		searchFormDTO.setFilterList(this.filterList);
		
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seShareDocSearch(searchFormDTO),searchFormDTO.getGridNames(),
				searchService.seShareDocSearchCount(searchFormDTO),this.posStart);
		
		return SUCCESS;
	}
	

	@Action(value = "seSearchFilter", results = { @Result(name = "success",   type= "json") })
	public String seSearchFilter() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("Start");
		searchFormDTO.setFilterName(filterName);
		this.jsonArray = searchService.seSearchFilter(searchFormDTO).toJSONArray();
		return SUCCESS;
	}
	
	//add_code 20151215 VM환경 추가
	@Action(value = "getVMDate", results = { @Result(name = "success",   type= "json") })
	public String getVMDate() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("Start");
		
		VMDTO vmDTO = new VMDTO();
		vmDTO.setVMCheck(edmConfig.getConfig("VMCheck"));
		vmDTO.setVMCheckOut(edmConfig.getConfig("VMCheckOut"));
		vmDTO.setVMDownload(edmConfig.getConfig("VMDownload"));
		vmDTO.setVMIp(edmConfig.getConfig("VMIp"));
		
		this.jsonObject = vmDTO.toJSONObject();
		return SUCCESS;
	}
	//end
	
	@Action(value = "seViewSearchRetryValue", results = { @Result(name = "success",   type= "text") })
	public String seViewSearchRetryValue() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("Start");
		this.xmlText = this.edmConfig.getConfig("seViewSearchRetryValue");
		return SUCCESS;
	}
	
	
	
	/***
	 * 조회 페이지를 초기값 설정과 로드합니다.
	 * 
	 * @author 김태성(bkmilk)
	 * @since 2011/04/11
	 * @return
	 */
	@Action(value = "seSearchTreeForItem", results = { @Result(name = "success", type = "text") })
	public String seSearchTree() throws ParserConfigurationException,
			ParseException {
		log.debug("Start");
		this.xmlText = DhxHtmlGridUtil.toTreeXml(
				searchService.seSearchTreeForItem(searchFormDTO),
				searchFormDTO.getGridNames());
		return SUCCESS;
	}
	
	/***
	 * 영역으로 조회합니다.
	 * 
	 * @author SHIN SANG HO
	 * @since 2016/02/25
	 * @return
	 */
	@Action(value = "seSearchTreeForArea", results = { @Result(name = "success", type = "text") })
	public String seSearchTreeForArea() throws ParserConfigurationException,
			ParseException {
		log.debug("Start");
		this.xmlText = DhxHtmlGridUtil.toTreeXml(
				searchService.seSearchTreeForArea(searchFormDTO),
				searchFormDTO.getGridNames());
		return SUCCESS;
	}

	
	
	/* 2014.09.18 WooChul Jung
	 * 		Upload file without revision. HDO Requirement
	*/
	@Action(value = "upFileInfo", results = { @Result(name = "success", type = "text") })
	public String upFileInfo() throws MsgException {
		log.debug("start upFileInfo");
		searchService.upFileInfo(doc_seq, file_id) ;

		return SUCCESS;
	}
	
	/* 2014.10.06 WooChul Jung
	 * 	Export excel with document information and the format is the same as which is used in Document Enrollment.
	
	*/
	@Action(value = "seDocExcel", results = { @Result(name = "success", type = "girdxml") })
	public String seDocExcel() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug(" Action : seDocExcel");

		if (EDMSUtil.isNullOrEmptyString(doc_seqs)) {
			return SUCCESS;
		}
		System.out.println(excel_form);
		String[] temp = doc_seqs.split(",") ;
		List<String> doc_seqList = new ArrayList<>() ;
		
		for (int i = 0 ; i < temp.length ; i++) doc_seqList.add(temp[i]) ;
		
		Map<String, Object> map = new HashMap<String, Object>() ;
		map.put("list", doc_seqList) ;
		
		RichList<DocumentDTO> resultDataList = searchService.seDocExcel(map);
		String gridIds = "";
		String[] gridIdArray = null;
		ArrayList<String> gridList = null;
		String newGridIds = "";
		DhxGirdExcelXmlUtil util = null;
		if("def".equals(excel_form))
		{
			
			//기본 설정 값
			gridIds = DefaultExcelGrid();
			gridIdArray = gridIds.split(",");
			gridList = new ArrayList<String>(
					Arrays.asList(gridIdArray));
			newGridIds = makeStringFromList(gridList);
			util = new DhxGirdExcelXmlUtil(resultDataList,
					newGridIds);
			DefaultExcelForm(util);
		}else if("personal".equals(excel_form))
		{
			//개인화 설정 값
			ComDTO ComDTO = new ComDTO();
			PersonalizationDTO pDTO = new PersonalizationDTO();
			pDTO.setType("SS");
			pDTO.setSes_emp_id(ComDTO.getSes_emp_id());
			
			gridIds = personalizationService.seGridIds(pDTO);
			RichList<GridColumnsDTO> gridInfoList = personalizationService.seGridInfo(pDTO);

			gridIdArray = gridIds.split(",");
			gridList = new ArrayList<String>(
					Arrays.asList(gridIdArray));
			
			newGridIds = makeStringFromList(gridList);
			util = new DhxGirdExcelXmlUtil(resultDataList,
					newGridIds);
			util.addHaed();
			
			for(GridColumnsDTO list : gridInfoList)
			{
				util.addColumn(list.getLabel(), Integer.toString((list.getWidth())),"center", list.getSort(), list.getType(),"");
				if(list.getCodecombo() != null && !"ext".equals(list.getId()))
					util.addOption(codeService.getColombs(list.getCodecombo()));
			}
		}
		this.xmlData = util;
		return SUCCESS;
	}
	
	// by HDO
	@Action(value = "seDocExcelInKindTree", results = { @Result(name = "success", type = "girdxml") })
	public String seDocExcelInKindTree() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug(" Action : seDocExcelInKindTree");

		searchFormDTO.setOrderValue(this.orderby,this.direct);
		searchFormDTO.setFilterList(this.filterList);
		
		RichList<?> doc_seqList = searchService.seDocExcelInKindTree(searchFormDTO);
		String gridIds = "";
		String[] gridIdArray = null;
		ArrayList<String> gridList = null;
		PersonalizationDTO pDTO = null;
		
		if("def".equals(excel_form))
		{
		//gridIds = "cnt,$check,result,file_name,doc_type,tree_id,tree_name,dockind_id,doc_no,doc_name,com_code,mr_code,mr_file_id,rev_no,size_id,grade_id,maintain_id,create_date,company_id,project_name,com_doc_no,item_nos,commts,excelfile_name,extcol$cabinet,extcol$binder" ;
		gridIds = DefaultExcelGrid();
		gridIdArray = gridIds.split(",");
		gridList = new ArrayList<String>(
				Arrays.asList(gridIdArray));
		}else if("personal".equals(excel_form))
		{
			ComDTO ComDTO = new ComDTO();
			pDTO = new PersonalizationDTO();
			pDTO.setType("SS");
			pDTO.setSes_emp_id(ComDTO.getSes_emp_id());
			
			gridIds = personalizationService.seGridIds(pDTO);
			RichList<GridColumnsDTO> gridInfoList = personalizationService.seGridInfo(pDTO);

			gridIdArray = gridIds.split(",");
			gridList = new ArrayList<String>(
					Arrays.asList(gridIdArray));
		}
		RichList<DocumentDTO> resultDataList = null ;
		
		for (int i = 0 ; i < doc_seqList.size() ; i = i + 900) {
			
			RichList<DocumentDTO> tempList = null ;
			Map<String, Object> map = new HashMap<String, Object>() ;
			
			if ((i+900) < doc_seqList.size()) {
				map.put("list", doc_seqList.subList(i, i+900)) ;
			} else {
				map.put("list", doc_seqList.subList(i, doc_seqList.size())) ;
			}
			tempList = searchService.seDocExcel(map);
			
			if (i == 0) {
				resultDataList = tempList ;	
			} else {
				resultDataList.addAll(tempList) ;	
			}				
		}
		
		String newGridIds = makeStringFromList(gridList);
		DhxGirdExcelXmlUtil util = new DhxGirdExcelXmlUtil(resultDataList,
				newGridIds);
		
		if("def".equals(excel_form))
		{
			DefaultExcelForm(util);
		}else if("personal".equals(excel_form))
		{
			RichList<GridColumnsDTO> gridInfoList = personalizationService.seGridInfo(pDTO);
			util.addHaed();
			
			for(GridColumnsDTO list : gridInfoList)
			{
				util.addColumn(list.getLabel(), Integer.toString((list.getWidth())),"center", list.getSort(), list.getType(),"");
				if(list.getCodecombo() != null && !"ext".equals(list.getId()))
					util.addOption(codeService.getColombs(list.getCodecombo()));
			}
		}
		this.xmlData = util;
		return SUCCESS;
	}
	
	public String DefaultExcelGrid()
	{
		return "empty,result,file_name,combine_path,doc_type,tree_id,tree_name,dockind_id,doc_no,doc_name,com_code,mr_code,mr_file_id,rev_no,size_id,grade_id,maintain_id,create_date,company_id,project_name,com_doc_no,item_nos,commts,excelfile_name,cabinet,binder,publicDoc,book_category";
	}
	public DhxGirdExcelXmlUtil DefaultExcelForm(DhxGirdExcelXmlUtil util) throws ParseException
	{
		util.addHaed();
		util.addColumn("", "110", "center", "ro", "str", "");
		util.addColumn(this.getText("W_STATUS"), "300", "center", "ro", "str",
				"");
		util.addColumn("*"+this.getText("W_FILE"), "320", "center", "ro", "str", "");
		util.addColumn(this.getText("W_COMBINE_FILE_PATH"), "320", "center", "ro", "str", "");
		util.addColumn("*"+this.getText("W_DATA_SHAPE"), "180", "center", "co",
				"str", "");
		util.addOption(codeService.getColombs("DOCTYPE"));
		util.addColumn("*"+this.getText("W_TREE_ID"), "180", "center", "ro", "str",
				"");
		util.addColumn(this.getText("W_TREE"), "220", "center", "ro", "str", "");
		util.addColumn("*"+this.getText("W_KIND"), "320", "center", "ro",
				"str", "");
		util.addColumn("*"+this.getText("W_DATA_NO"), "320", "center", "ro", "str",
				"");
		util.addColumn("*"+this.getText("W_DATA_NAME"), "320", "center", "ro",
				"str", "");

		util.addOption(codeService.getColombs("COM_CODE"));
		util.addColumn("*"+this.getText("W_DATA_COM_CODE"), "200", "center", "co",
				"str", "");
		util.addColumn(this.getText("W_DATA_MR_CODE"), "320", "center", "ro",
				"str", "");
		util.addColumn(this.getText("W_MR_FILE_ID"), "320", "center", "ro",
				"str", "");
		
		util.addColumn("*"+this.getText("W_REV"), "160", "center", "ro", "str", "");
		util.addColumn("*"+this.getText("W_DATA_SIZE"), "200", "center", "co",
				"str", "");
		util.addOption(codeService.getColombs("SIZE"));
		util.addColumn("*"+this.getText("W_DATA_GRADE"), "200", "center", "co",
				"str", "");
		util.addOption(codeService.getColombs("GRADE"));
		util.addColumn("*"+this.getText("W_MAINTAIN"), "200", "center", "co",
				"str", "");
		util.addOption(codeService.getColombs("MAINT"));
		util.addColumn(this.getText("W_MAKE_DATE"), "220", "center", "ro",
				"str", "");
		util.addColumn(this.getText("W_OUTCOM_COMPANY"), "200", "center", "co",
				"str", "");
		util.addOption(codeService.getColombs("COMPANY"));
		util.addColumn("*"+this.getText("W_PROJECT_NAME"), "220", "center", "ro",
				"str", "");
		util.addColumn(this.getText("W_EXTERNAL_DOC_NO"), "320", "center",
				"ro", "str", "");
		util.addColumn(this.getText("W_DATA_PARTCODE"), "400", "center", "ro", "str",
				"");
		util.addColumn(this.getText("W_REMARK_Desc"), "320", "center", "ro", "str",
				"");
		util.addColumn(this.getText("W_EXCEL_FILE_NAME"), "320", "center", "ro", "str",
				"");
		
		util.addColumn(this.getText("CABINET"), "320", "center", "ro", "str",
				"");
		util.addColumn(this.getText("BINDER"), "320", "center", "ro", "str",
				"");
		util.addColumn(this.getText("W_PUBLIC_DOC"), "320", "center", "ro", "str",
				"");
		util.addColumn(this.getText("W_BOOK_CATEGORY"), "320", "center", "ro", "str",
				"");
		return util;
		
	}
	@Action(value = "deCADData", results = { @Result(name = "success", type = "text") })
	public String deCADData() throws IOException,
			IllegalArgumentException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		log.debug("start deCADData");
		searchService.deCADDataTextFromDB(doc_seq) ;
		this.xmlText = "1" ;
		return SUCCESS;
	}

	private String makeStringFromList(List<String> gridList) {
		String returnValue = "";
		for (String gridId : gridList) {
			returnValue += gridId + ",";
		}
		returnValue = returnValue.substring(0, returnValue.length() - 1);
		return returnValue;
	}
	
	
	public void setCheckOutDTO(CheckOutDTO checkOutDTO) {
		this.checkOutDTO = checkOutDTO;
	}

	public void setCompanylist(LinkedHashMap<String, String> companylist) {
		this.companylist = companylist;
	}

	public void setExtlist(LinkedHashMap<String, String> extlist) {
		this.extlist = extlist;
	}

	public void setGradecodelist(LinkedHashMap<String, String> gradecodelist) {
		this.gradecodelist = gradecodelist;
	}

	public void setMaintcodelist(LinkedHashMap<String, String> maintcodelist) {
		this.maintcodelist = maintcodelist;
	}

	public void setPrintcodelist(LinkedHashMap<String, String> printcodelist) {
		this.printcodelist = printcodelist;
	}

	@Override
	public void setSearchFormDTO(SearchDocFormDTO searchFormDTO) {
		this.searchFormDTO = searchFormDTO;
	}

	public void setSizelist(LinkedHashMap<String, String> sizelist) {
		this.sizelist = sizelist;
	}

	public void setTypecodelist(LinkedHashMap<String, String> typecodelist) {
		this.typecodelist = typecodelist;
	}

	public String getTreeRoot() {
		return treeRoot;
	}

	public void setTreeRoot(String treeRoot) {
		this.treeRoot = treeRoot;
	}

	public String getKindRoot() {
		return kindRoot;
	}

	public void setKindRoot(String kindRoot) {
		this.kindRoot = kindRoot;
	}

	public LinkedHashMap<String, String> getExtcollist() {
		return extcollist;
	}

	public void setExtcollist(LinkedHashMap<String, String> extcollist) {
		this.extcollist = extcollist;
	}
	
	@Action(value = "seDocSeq", results = { @Result(name = "success", type = "text") })
	public String seDocSeq() throws Exception {
		log.debug("start");
		if (searchFormDTO == null)
			searchFormDTO = new SearchDocFormDTO();
		this.xmlText += searchService.seDocSeq(searchFormDTO);
		return SUCCESS;
	}

	@Action(value = "seGetDocNo", results = { @Result(name = "success", type = "text") })
	public String seGetDocNo() throws Exception {
		log.debug("start");
		if (searchFormDTO == null)
			searchFormDTO = new SearchDocFormDTO();
		this.xmlText += searchService.seGetDocNo(searchFormDTO);
		return SUCCESS;
	}
	
	@Action(value = "seTagSearch", results = { @Result(name = "success",  type= "girdxml") })
	public String seTagSearch() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("Start seTagSearch");
		
		if(searchFormDTO == null) 
			searchFormDTO = new SearchDocFormDTO();
		
		
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seTagSearch(searchFormDTO),searchFormDTO.getGridNames());
		
		return SUCCESS;
	}
	
	//add_code 20161202 나만의도면보기 추가 yoocg
	@Action(value = "seMyView", results = { @Result(name = "success",  type= "girdxml") })
	public String seMyView() throws Exception {
		log.debug("start");
		if (searchFormDTO == null)
			searchFormDTO = new SearchDocFormDTO();
		
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seMyView(searchFormDTO),searchFormDTO.getGridNames());
		return SUCCESS;
	}
	
	@Action(value = "seMyViewHis", results = { @Result(name = "success",  type= "girdxml") })
	public String seMyViewHis() throws Exception {
		log.debug("start");
		if (searchFormDTO == null)
			searchFormDTO = new SearchDocFormDTO();
		
		this.xmlData = new DhxGirdXmlUtil(searchService.seMyViewHis(searchFormDTO),searchFormDTO.getGridNames());
		return SUCCESS;
	}

	@Action(value = "seMyViewDel", results = { @Result(name = "success", type = "text") })
	public String seMyViewDel() throws IOException,
			IllegalArgumentException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		log.debug("start");
		HttpServletRequest request = ServletActionContext.getRequest();
		String emp_id  = request.getParameter("emp_id");	
		String doc_seq = request.getParameter("doc_seq");

		this.xmlText = searchService.seMyViewDel(emp_id, doc_seq);
		return SUCCESS;
	}	
	
	//end

	
	@Action(value = "seDocCnt", results = { @Result(name = "success", type = "json") })
	public String seDocCnt() {
		log.debug("Start seTreeCnt");
		HttpServletRequest request = ServletActionContext.getRequest();
		String category = request.getParameter("category");
		
		if(searchFormDTO == null) 
			searchFormDTO = new SearchDocFormDTO();
		
		//treeDTO.setSearchFormDTO(searchFormDTO);
		searchFormDTO.getDocumentDTO().setTree_id("");
		searchFormDTO.getDocumentDTO().setDockind_id("");
		
		if("K".equals(category))
			this.jsonArray = searchService.seTreeCnt(searchFormDTO).toJSONArray();
		else if("T".equals(category))
			this.jsonArray = searchService.seKindCnt(searchFormDTO).toJSONArray();
		
		return SUCCESS;
	}

	
	@Action(value = "searchExcel", results = { @Result(name = "success", type="girdxml") })
	public String seUserExcel() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception  {
				
//		HttpServletRequest request = ServletActionContext.getRequest();
//		
//		int curpage =  Integer.parseInt(request.getParameter("curpage"));
//		
//		if(curpage <= 1){
//			this.posStart = -1;
//			this.count = -1;
//		}else{
//			this.posStart = (curpage-1) * 500;
//			this.count = curpage * 500;
//		}
		if(this.posStart == -1)
		{
			seSearchCount("SC");
		}
		searchFormDTO.setPageValue(this.posStart,this.count);
		
		if (!EDMSUtil.isNullOrEmptyString(this.orderby) && ("doc_no".equals(this.orderby))) {
			this.orderby = "doc_no, rev_no" ;
		}
		searchFormDTO.setOrderValue(this.orderby,this.direct);
		searchFormDTO.setFilterList(this.filterList);
		
		DhxGirdExcelXmlUtil util =new DhxGirdExcelXmlUtil(searchService.seSearchExcel(searchFormDTO),searchFormDTO.getGridNames());
		
		util.addHaed();
		util.addColumn("ext","60","center","ro","str","");
		util.addColumn(this.getText("W_SHAPE"),"150","center","ro","str","");
		util.addOption(codeService.getColombs("DOCTYPE"));
		util.addColumn(this.getText("W_DATA_NO"),"150","left","ro","str","");		
		util.addColumn(this.getText("W_REV"),"50","center","ro","str","");
		util.addColumn(this.getText("W_DATA_TREE"),"150","left","ro","str","");
		util.addColumn(this.getText("W_DATA_SIZE"),"100","left","ro","str","");
		util.addColumn(this.getText("W_DATA_NAME"),"150","center","ro","str","");
		util.addColumn(this.getText("W_DRAWING_NO"),"150","center","ro","str","");
		util.addColumn(this.getText("W_PROJECT_NAME"),"150","left","ro","str","");
		util.addColumn(this.getText("W_CONSTRUCTION_VENDOR"),"150","left","ro","str","");
		util.addColumn(this.getText("W_DESIGN_VENDOR"),"150","left","ro","str","");
		util.addColumn(this.getText("W_CONSTRUCTION_NO"),"150","left","ro","str","");
		util.addColumn(this.getText("TPIC"),"150","left","ro","str","");
		util.addColumn(this.getText("W_COMPLETION_DATE"),"150","left","ro","str","");
		util.addColumn(this.getText("W_UPDATE_DATE"),"150","center","ro","str","");	
	
		this.xmlData = util;
		return SUCCESS;
		
	}
	
	@Action(value = "seRoleCheck", results = { @Result(name = "success", type = "text") })
	  public String seRoleCheck() throws Exception {
	   log.debug("start");
	   
	   HttpServletRequest request = ServletActionContext.getRequest();
		String doc_seq = request.getParameter("doc_seq");
		String emp_id = request.getParameter("emp_id");	
		SearchDocFormDTO searchFormDTO = new SearchDocFormDTO();
		searchFormDTO.setDoc_seq(doc_seq);
		searchFormDTO.setEmp_id(emp_id);
	   
	   if (!"".equals(emp_id)){
	    this.xmlText = searchService.seRoleCheck(searchFormDTO);
	   }
	   
	   return SUCCESS;
	  }
	
	
	@Action(value = "sePdfConverter", results = { @Result(name = "success",  type= "text") })
	public String sePdfConverter() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug(" Action : sePdfConverter");
		RichList<DocumentDTO> resultDataList = searchService.pdfConverterInfo(searchFormDTO);
		//해당 되는 경로에 pdf 변환 프로그램 넣어 주고 서버 디렉토리 경로 변경해주기
		//searchFormDTO
		Process p = Runtime.getRuntime().exec("D:\\OpenMinds\\OdPdfExportEx\\OdPdfExportEx.exe " + edmConfig.getSavePath() + resultDataList.get(0).getSvr_path().replaceAll("/", Matcher.quoteReplacement("\\"))
				+resultDataList.get(0).getFile_id()+".dwg " + edmConfig.getSavePath() + resultDataList.get(0).getSvr_path().replaceAll("/", Matcher.quoteReplacement("\\"))
				+resultDataList.get(0).getFile_id()+".pdf  0");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		while((line = br.readLine()) != null)
		{
			System.out.println(line);
		}
		p.getErrorStream().close();
		p.getInputStream().close();
		p.getOutputStream().close();
		p.waitFor();
		return SUCCESS;

	}
	
	@Action(value = "sePdfView", results = { @Result(name = "success",  type= "json") })
	public String sePdfView() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug(" Action : sePdfConverter");
		HttpServletRequest request = ServletActionContext.getRequest();
		String doc_seq = request.getParameter("doc_seq");
		System.out.println(doc_seq);
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setDoc_seq(Integer.parseInt(doc_seq));
		this.jsonArray = searchService.sePdfView(documentDTO).toJSONArray();
		return SUCCESS;

	}
	
	@Action(value = "seDwgView", results = { @Result(name = "success",  type= "json") })
	public String seDwgView() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug(" Action : sePdfConverter");
		HttpServletRequest request = ServletActionContext.getRequest();
		String doc_seq = request.getParameter("doc_seq");
		System.out.println(doc_seq);
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setDoc_seq(Integer.parseInt(doc_seq));
		this.jsonArray = searchService.seDwgView(documentDTO).toJSONArray();
		return SUCCESS;

	}

	//출력요청 그리드화면 
	@Action(value = "seRPDocList", results = { @Result(name = "success",  type= "girdxml") })
	public String seRPDocList() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seRPDocList(searchFormDTO),searchFormDTO.getGridNames3());
		return SUCCESS;
	}
	
	//다운로드요청 그리드화면 
	@Action(value = "seRDDocList", results = { @Result(name = "success",  type= "girdxml") })
	public String seRDDocList() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seRDDocList(searchFormDTO),searchFormDTO.getGridNames3());
		return SUCCESS;
	}
	
	//문서조회에서 출력으로 선택한 파일리스트 띄우기
	@Action(value = "sePrintList", results = { @Result(name = "success",  type= "girdxml") })
	public String sePrintList() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.sePrintList(outSourcingDTO),outSourcingDTO.getGridNames());
		return SUCCESS;
	}
	
	//문서조회에서 외주배포용으로 선택한 파일리스트 띄우기
	@Action(value = "seOutPublishDoc", results = { @Result(name = "success",  type= "girdxml") })
	public String seOutPublishDoc() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seOutPublishDoc(searchFormDTO),searchFormDTO.getGridNames3());
		return SUCCESS;
	}
	
	//외주배포용 외주업체 담당자 조회
	@Action(value = "seApprovalInfo", results = { @Result(name = "success", type = "json") })
	public String seApprovalInfo() throws ParserConfigurationException, ParseException {
		log.debug("Start");
		HttpServletRequest request = ServletActionContext.getRequest();
		String company_code = request.getParameter("company_code");

		this.jsonArray = searchService.seApprovalInfo(company_code).toJSONArray();
		
		
		return SUCCESS;
	}
	
	
	//문서조회에서 수정요청 || 외주수정요청으로 선택한 파일리스트 띄우기
	@Action(value = "seRCDocList", results = { @Result(name = "success",  type= "girdxml") })
	public String seRCDocList() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seRCDocList(searchFormDTO),searchFormDTO.getGridNames());
		return SUCCESS;
	}
	
	/*
	 * 2020-02-18
	 * 외주사용자에게 동일한 문서를 변경요청하였는지 중복체크
	 * 김경호
	 */
	@Action(value = "seOCODocDupChk", results = { @Result(name = "success", type = "text") })
	  public String seOCODocDupChk() throws Exception {
	   log.debug("start");
	   
	   int result = searchService.seOCODocDupChk(workFlowFormDTO);
	   if(result > 0){
		   this.xmlText = "ERROR";
	   }else{
		   this.xmlText = "SUCCESS";
	   }
   
	   return SUCCESS;
	  }
	
	@Action(value = "checkLock", results = { @Result(name = "success", type = "text") })
	public String checkLock() throws Exception {
		this.xmlText = searchService.seCheckLock(searchFormDTO.getDocumentDTO());
		return SUCCESS;
	}
	
	
	@Action(value = "ApprovalList", results = {@Result(name = "success",   type= "json") })	
	public String ApprovalList() throws Exception {
		log.debug("ApprovalList Start");

		this.jsonArray =searchService.ApprovalList().toJSONArray();
		return SUCCESS;
	}
	
	
	@Action(value = "seAddSharedList", results = { @Result(name = "success",  type= "girdxml") })
	public String seAddSharedList() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("seAddSharedList Start");
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seAddSharedList(searchFormDTO),searchFormDTO.getGridNames());
		return SUCCESS;
	}
	
	@Action(value = "inShareFolderDoc", results = { @Result(name = "success",  type= "text") })
	public String inShareFolderDoc() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("inShareFolderDoc Start");
		int res = searchService.inShareFolderDoc(searchFormDTO);
		if(res > 0){
			this.xmlText = "SUCCESS";
		}else{
			this.xmlText = "ERROR";
		}
		return SUCCESS;
	}
	
	//공유폴더 담기목록 리스트 삭제
	@Action(value = "deShareListDel", results = { @Result(name = "success",  type= "text") })
	public String deShareListDel() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("deShareListDel Start");
		int res = searchService.deShareListDel(searchFormDTO);
		if(res > 0){
			this.xmlText = "SUCCESS";
		}else{
			this.xmlText = "ERROR";
		}
		return SUCCESS;
	}
	
	//공유폴더 내 파일 제거
	@Action(value = "deShareDocDel", results = { @Result(name = "success",  type= "text") })
	public String deShareDocDel() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("deShareDocDel Start");
		int res = searchService.deShareDocDel(searchFormDTO);
		if(res > 0){
			this.xmlText = "SUCCESS";
		}else{
			this.xmlText = "ERROR";
		}
		return SUCCESS;
	}
	
	
	@Action(value = "attrChange", results = { @Result(name = "success",  type= "text") })
	public String attrChange() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("attrChange Start");
		int res = 0;
		
		if("doc_no".equals(documentDTO.getAttr_kind()) || "rev_no".equals(documentDTO.getAttr_kind())
				|| "publicDoc".equals(documentDTO.getAttr_kind()) || "doc_name".equals(documentDTO.getAttr_kind())) {
			if(documentDTO.getChange_text() == null && "".equals(documentDTO.getChange_text())) {
				this.xmlText = "내용을 입력하지 않았습니다.";
				return SUCCESS;
			}
		}
		
		documentDTO.setChange_doc_list(documentDTO.getDoc_seq_list().split(","));
		
		if("doc_no".equals(documentDTO.getAttr_kind()) || "rev_no".equals(documentDTO.getAttr_kind())) {
			SearchDocFormDTO dto = new SearchDocFormDTO();
			dto.setDocumentDTO(documentDTO);
			res = searchService.attrChangeDocNo(dto);
		}else {
			res = searchService.attrChange(documentDTO);
			log.debug(">>>>>>>>>> Action res : " + res);
		}
		
		if(res > 0){
			this.xmlText = "SUCCESS";
		}else{
			this.xmlText = "수정이 정상적으로 되지 않았습니다.";
		}
		
				
		return SUCCESS;
	}
	
	
	@Action(value = "previewSearch", results = { @Result(name="success", type = "text")})
	public String esSearch() throws Exception{
		
		
		List<Map<String, Object>> list = searchService.previewSearch(searchFormDTO);
	
		ObjectMapper mapper = new ObjectMapper();
		this.xmlText = mapper.writeValueAsString(list);
		
		return SUCCESS;
	}
	
	@Action(value = "seOutDistributeReqListDupChk", results = { @Result(name = "success",  type= "text") })
	public String seOutDistributeReqListDupChk() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("seOutDistributeReqListDupChk Start");
		HttpServletRequest request = ServletActionContext.getRequest();
		String doc_seq_list = request.getParameter("doc_seq_list");
		String emp_id = request.getParameter("emp_id");

		this.xmlText = searchService.seOutDistributeReqListDupChk(doc_seq_list,emp_id);
		
		return SUCCESS;
		
	}
	//한화 외주배포 리스트 띄우기
	@Action(value = "seOutDistributeReqList", results = { @Result(name = "success",  type= "girdxml") })
	public String seOutDistributeReqList() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("seOutDistributeReqList Start");
		HttpServletRequest request = ServletActionContext.getRequest();
		String doc_seq_list = request.getParameter("doc_seq_list");
		String emp_id = request.getParameter("emp_id");
		DocumentDTO dto = new DocumentDTO();
		dto.setDoc_seq_list(doc_seq_list);
		dto.setEmp_id(emp_id);
		distributeMngDTO.setDocumentDTO(dto);
		sizelist = codeService.getColombs("SIZE");
		printcodelist = codeService.getColombs("PLOTCAUSE");
		extcollist = codeService.getColombs("EXTEND_COL");	
		typecodelist = new LinkedHashMap<String, String>();
		typecodelist.put("", this.getText("W_ALL"));
		typecodelist.putAll(codeService.getColombs("DOCTYPE"));	 
		extlist = new LinkedHashMap<String, String>();
		extlist.put("", this.getText("W_ALL"));
		extlist.putAll(searchService.seExts());
		comcodelist = new LinkedHashMap<String, String>();
		comcodelist.put("", this.getText("W_ALL"));
		comcodelist.putAll(codeService.getColombs("COM_CODE"));
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seOutDistributeReqList(distributeMngDTO),distributeMngDTO.getGridNames());
		return SUCCESS;
	}
	
	//한화 MoC번호 리스트 띄우기
	@Action(value = "seMocNoSelectInfoList", results = { @Result(name = "success",  type= "girdxml") })
	public String seMocNoSelectInfoList() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("seMocNoSelectInfoList Start");
		HttpServletRequest request = ServletActionContext.getRequest();
		String emp_id = request.getParameter("emp_id");
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seMocNoSelectInfoList(searchFormDTO),searchFormDTO.getGridNames());
		return SUCCESS;
	}
	
	//한화 MoC번호 리스트 띄우기
	@Action(value = "seCompanyInfoSelect", results = { @Result(name = "success",  type= "girdxml") })
	public String seCompanyInfoSelect() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("seCompanyInfoSelect Start");
		HttpServletRequest request = ServletActionContext.getRequest();
		String emp_id = request.getParameter("emp_id");
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.seCompanyInfoSelect(searchFormDTO),searchFormDTO.getGridNames());
		return SUCCESS;
	}
	
	//최근 검색이력 조회
	@Action(value = "getRecentSearchWord", results = { @Result(name = "success",  type= "json") })
	public String getRecentSearchWord() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("getRecentSearchWord Start");
		HttpServletRequest request = ServletActionContext.getRequest();
		String emp_id = request.getParameter("emp_id");
		String page_uri = request.getParameter("page_uri");
		String word = request.getParameter("word");
		this.jsonArray = searchService.getRecentSearchWord(emp_id,page_uri,word).toJSONArray();
		
		return SUCCESS;
	}
	
	//최근 검색어 전체 삭제
	@Action(value = "deRecentSearchWord", results = { @Result(name = "success",  type= "text") })
	public String deRecentSearchWord() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("deRecentSearchWord Start");
		HttpServletRequest request = ServletActionContext.getRequest();
		String emp_id = request.getParameter("emp_id");
		String page_uri = request.getParameter("page_uri");
		String value = request.getParameter("value");
		this.xmlText = Integer.toString(searchService.deRecentSearchWord(emp_id,page_uri,value));
		
		return SUCCESS;
	}
	/**
	 * 분류트리에 따른 Doc_no 조회
	 * @return
	 * @throws UnexpectedInputException
	 * @throws org.springframework.batch.item.ParseException
	 * @throws Exception
	 */
	@Action(value = "getTreeDocNo", results = { @Result(name = "success",  type= "text") })
	public String getTreeDocNo() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("getTreeDocNo Start");
		HttpServletRequest request = ServletActionContext.getRequest();
		String tid = request.getParameter("tid");
		
		this.xmlText = searchService.getTreeDocNo(tid);
		
		return SUCCESS;
	}
	
	@Action(value = "getDocExtCombo", results = { @Result(name = "success",  type= "json") })
	public String getDocExtCombo() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("getTreeDocNo Start");
		
		this.jsonArray = codeService.getDocExtCombo().toJSONArray();
		
		return SUCCESS;
	}
		
	@Action(value = "getDocTypeCombo", results = { @Result(name = "success",  type= "json") })
	public String getDocTypeCombo() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("getTreeDocNo Start");
		HttpServletRequest request = ServletActionContext.getRequest();
		String category = request.getParameter("category");
		
		this.jsonArray = codeService.getDocTypeCombo(category).toJSONArray();
		
		return SUCCESS;
	}
	
	/**
	 * 변경요청 Markup File 선택
	 * @return
	 * @throws JsonProcessingException 
	 * @throws ParseException 
	 * @throws ParserConfigurationException 
	 * @throws Exception
	 */
	@Action(value = "markupFileList", results = { @Result(name = "success",  type= "girdxml") })
	public String markupFileList() throws ParserConfigurationException, ParseException {
		log.debug("markupFileList Start");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		ActionContext context = ActionContext.getContext();
		sessionDTO = (SessionDTO) context.getSession().get("sessionDTO");
		String doc_seq = request.getParameter("doc_seq");
		String emp_id = sessionDTO.getEmpDTO().getEmp_id();
		
		this.xmlData = DhxHtmlGridUtil.toXml(searchService.markupFileList(doc_seq,emp_id),searchFormDTO.getGridNames());
		
		return SUCCESS;
	}
	
	public void seSearchCount(String type) throws Exception {
		ComDTO ComDTO = new ComDTO();
		PersonalizationDTO dto = new PersonalizationDTO();
		dto.setType(type);
		dto.setSes_emp_id(ComDTO.getSes_emp_id());
		
		int cnt = personalizationService.seMySearchCount(dto);
	    
		if(cnt == 0)
	    {
	    	cnt = 200;
	    }
		this.count = cnt;
	}
	
	@Action(value = "seAttrChangeList", results = { @Result(name = "success", type = "json") })
	public String seAttrChangeList() throws IOException {
		log.debug(" Action : seAttrChangeList");

		HttpServletRequest request = ServletActionContext.getRequest();
		String emp_id = request.getParameter("emp_id");

		RichList<PersonalizationDTO> attrList = personalizationService.seAttrChangeList(emp_id) ;
		this.jsonArray = attrList.toJSONArray();

		return SUCCESS;
	}
	
	@Action(value = "seCommtsList", results = { @Result(name = "success", type="json") })
	public String seCommtsList() throws ParserConfigurationException {
		log.debug(" Action : seCommtsList");
		this.jsonArray = searchService.seCommtsList().toJSONArray();
		return SUCCESS;
	}
	
	
	@Action(value = "getSearchExtCombo", results = { @Result(name = "success",  type= "json") })
	public String getSearchExtCombo() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		log.debug("getSearchExtCombo Start");
		
		this.jsonArray = searchService.getSearchExtCombo(searchFormDTO).toJSONArray();
		
		return SUCCESS;
	}
	
	
	
	
	public String getDefault_date() {
		return default_date;
	}
	public void setDefault_date(String default_date) {
		this.default_date = default_date;
	}
	public WorkFlowFormDTO getWorkFlowFormDTO() {
		return workFlowFormDTO;
	}
	public void setWorkFlowFormDTO(WorkFlowFormDTO workFlowFormDTO) {
		this.workFlowFormDTO = workFlowFormDTO;
	}
	public LinkedHashMap<String, String> getCompany_List() {
		return Company_List;
	}
	public void setCompany_List(LinkedHashMap<String, String> company_List) {
		Company_List = company_List;
	}
	public LinkedHashMap<String, String> getOutlist() {
		return outlist;
	}
	public void setOutlist(LinkedHashMap<String, String> outlist) {
		this.outlist = outlist;
	}
	public String getDoc_seqs() {
		return doc_seqs;
	}
	public LinkedHashMap<String, String> getRuleNoList() {
		return ruleNoList;
	}
	public void setRuleNoList(LinkedHashMap<String, String> ruleNoList) {
		this.ruleNoList = ruleNoList;
	}
	public void setDoc_seqs(String doc_seqs) {
		this.doc_seqs = doc_seqs;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getGridNames() {
		return gridNames;
	}
	public void setGridNames(String gridNames) {
		this.gridNames = gridNames;
	}
	public String getDoc_seq() {
		return doc_seq;
	}
	public void setDoc_seq(String doc_seq) {
		this.doc_seq = doc_seq;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}	
	public CheckOutDTO getCheckOutDTO() {
		return checkOutDTO;
	}
	public LinkedHashMap<String, String> getCompanylist() {
		return companylist;
	}
	public LinkedHashMap<String, String> getExtlist() {
		return extlist;
	}
	public LinkedHashMap<String, String> getGradecodelist() {
		return gradecodelist;
	}
	public LinkedHashMap<String, String> getMaintcodelist() {
		return maintcodelist;
	}
	public LinkedHashMap<String, String> getPrintcodelist() {
		return printcodelist;
	}
	@Override
	public SearchDocFormDTO getSearchFormDTO() {
		return searchFormDTO;
	}
	public LinkedHashMap<String, String> getSizelist() {
		return sizelist;
	}
	public LinkedHashMap<String, String> getTypecodelist() {
		return typecodelist;
	}
	public CodeService getCodeService() {
		return codeService;
	}
	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	public TreeDTO getTreeDTO() {
		return treeDTO;
	}
	public void setTreeDTO(TreeDTO treeDTO) {
		this.treeDTO = treeDTO;
	}

	public OutSourcingDTO getOutSourcingDTO() {
		return outSourcingDTO;
	}

	public void setOutSourcingDTO(OutSourcingDTO outSourcingDTO) {
		this.outSourcingDTO = outSourcingDTO;
	}
	public DistributeMngDTO getDistributeMngDTO() {
		return distributeMngDTO;
	}
	public void setDistributeMngDTO(DistributeMngDTO distributeMngDTO) {
		this.distributeMngDTO = distributeMngDTO;
	}
	
	
}
