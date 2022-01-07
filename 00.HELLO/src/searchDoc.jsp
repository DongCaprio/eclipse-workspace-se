
<%@page import="com.edms.service.config.ConfigService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="/tags/OpenTag.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">

<jsp:include page="/std/com/includeJSP.jsp"/>
<script src="<s:url value='/std/dc/searchDoc.js'/>"></script>
<style>
.prog{
       width: 500px;
       background: rgb(189, 177, 177);
}
.progs{
    width: 0%;
    height: 50px;
    /* background: rgb(225, 149, 240); */
    background: skyblue; 
    color:#fff;
    text-align: center;
    line-height: 50px;
}
</style>

<script>

	var loading = false;
	var kindTree = null;
	var conTree = null;
	var favoriteTree = null;
	var sharedTree = null;
	var savedTreeIdArray = [];
	var savedKindIdArray = [];
	var savedFavoriteIDArray = [];
	var tab;
	var tabC = false; // 공유폴더 탭	
	var tabD = false; // 즐겨찾기 탭	
	var tree_layout = null;
	var tempPath;
	var buttonCheck = 1; // 1 : grid, 2 : prview
	var myContextMenu = null;
	var myDocContextMenu = null;
	var dataviewImg = false;
	var treeTopId="";
	var kindTopId="";
	let dateString = null;
	var exceptList = null;
	let except_check = false;
	function init() {
		
		var check = j$('#grayScale');
		check.click(function(){
			if(check[0].checked){
				grayScaleClassNm = 'gray';
				j$('.doc_img_org').attr('class','doc_img_gray');
			}else{
				grayScaleClassNm = 'imgOrg';
				j$('.doc_img_gray').attr('class','doc_img_org');
			}
			j$(".grayScale_toggle").toggle();
		});
		
		// 드래그앤드랍 트리변경 권한 체크
		if(toolbar.cont.innerText.indexOf($L("W_CHANGE_TREE_HD")) == -1){
			dndChngTreeRole = false;
		}else{
			dndChngTreeRole = true;
		}
		
		
		$('toolbarObj').hide();
		var Cal_1 = new dhtmlXCalendarObject({
	    	input : "distribute_enddate",
	    	button : "cal_distribute_enddate"
	    });
		Cal_1.setDateFormat("%Y-%m-%d");
		var Cal_2 = new dhtmlXCalendarObject({
	    	input : "checkin_date",
	    	button : "cal_checkin_date"
	    }); 
		Cal_2.setDateFormat("%Y-%m-%d");
		var Cal_3 = new dhtmlXCalendarObject({
	    	input : "attrchange_date",
	    	button : "cal_attrchange_date"
	    }); 
		Cal_3.setDateFormat("%Y-%m-%d");
		var Cal_4 = new dhtmlXCalendarObject({
	    	input : "expired_date",
	    	button : "cal_expired_date"
	    });
		Cal_4.setDateFormat("%Y-%m-%d");
	    var Cal_5 = new dhtmlXCalendarObject({
	    	input : "expired_change_date",
	    	button : "cal_expired_change_date"
	    });
		Cal_5.setDateFormat("%Y-%m-%d");
		
		//document.getElementById("grid_change").style.opacity = "0.3";
		
		// 문서 그리드 조회 image 미리보기를 위한 mouseover event(팝업 위치 계산)
	 	j$('#gridbox').mouseover(function(event){
	 		event = event || window.event; 
		 	 if ( event.pageX ) {  
			        tooltipX = event.pageX; 
			        tooltipY = event.pageY; 
			 } 
			 else { // 그외 브라우저용 
				 tooltipX = event.clientX + document.body.scrollLeft + document.documentElement.scrollLeft; 
				 tooltipY = event.clientY + document.body.scrollTop + document.documentElement.scrollTop; 
		        }	
	 	});
		
	 	// 마우스 이동 시 미리보기 팝업 하이드
	 	j$(document).mousemove(function(event){
	 		clearTimeout(img_tooltip);
	 		if(myPop){
		 		myPop.hide();
	 		}
	 	});
	 	// 마우스 이동 시 미리보기 팝업 하이드
	 	j$(parent.document).mousemove(function(event){
	 		clearTimeout(img_tooltip);
	 		if(myPop){
		 		myPop.hide();
	 		}
	 	});
		
	 	  
	 	// 문서분류 Context Menu
		if(myContextMenu == null){
			myContextMenu = new dhtmlXMenuObject({
				icons_path : _contextPath + "/img/ico/",
				context : true,
				items : [
						{id : "itemText"},
						{type : "separator"},
						{id : "new", text : "새폴더", img:"folderOpen.gif"},
						{id : "del", text : "폴더삭제", img:"discard.png"}
				]
			})
			
			myContextMenu.attachEvent("onClick", function(id){
				if(id == 'new'){
					doCreateTree(mytree.contextID);	
				}
				if(id == 'del'){
					removeTree(mytree.contextID);
				}
			})
			
		}
	 	
		// 내문서함 Context Menu
		if(myDocContextMenu == null){
			myDocContextMenu = new dhtmlXMenuObject({
				icons_path : _contextPath + "/img/ico/",
				context : true,
				items : [
						{id : "itemText"},
						{type : "separator"},
						{id : "new", text : "새폴더", img:"folderOpen.gif"},
						{id : "del", text : "폴더삭제", img:"discard.png"}
				]
			})
			
			myDocContextMenu.attachEvent("onClick", function(id){
				if(id == 'new'){
					createMyTree(mydoctree.contextID);	
				}
				if(id == 'del'){
					if(mydoctree.contextID == "1"){
						alert("최상위 트리는 삭제하실 수 없습니다.");
					}else{
						removeMyTree(mydoctree.contextID);
					}
				}
			})
			
		}
		
		// DataView Context Menu
		dvContextMenu = new dhtmlXMenuObject("contextArea");
		dvContextMenu.renderAsContextMenu();
		dvContextMenu.setIconPath(_contextPath + "/img/ico/");
		var url = _contextPath + "/edms/mn/seButton.action";
		new Ajax.Request(url, {
			method : 'POST',
			parameters : "curi=/std/dc/searchDoc.jsp",
			asynchronous : false,
			onSuccess : function(response) {
				var json = eval('(' + response.responseText + ')');
				var menu_add_cnt = 0;
				for ( var i = 0; i < json.size(); i++) {
					var button = json[i];
					
					if(button.lang_code != "")
						button.name = $L(button.lang_code); 

					if(menu_add_cnt == 0) dvContextMenu.addNewChild(dvContextMenu.topId, "RevSearch", "RevSearch", $L("W_REV_SEARCH"), false, "search.png", "");
					menu_add_cnt++;
					
					if (button.button_type == 'BUTTON' && button.name != "" && button.button_id != "search") {
						dvContextMenu.addNewChild(dvContextMenu.topId, button.button_id, button.button_id,
								$L(button.name), false, button.img_enabled,button.img_disenabled);
					}

					if (button.tip != null && button.tip != "")
						dvContextMenu.setTooltip(button.button_id, $L(button.tip));
				}
			}
		});
		
		// 문서 분류별 권한이 필요한 사용자에 대한 초기 메뉴 세팅
		if(_dwgAdminCheck){
			dvContextMenu.showItem('RequestChange');
			dvContextMenu.showItem('RequestDelete');
			dvContextMenu.showItem('RequestDownload');
			dvContextMenu.showItem('RequestPrint');
			
			dvContextMenu.showItem('CheckOut');
			dvContextMenu.showItem('Delete');
			dvContextMenu.showItem('MultiDownload');
			dvContextMenu.showItem('Print');
			dvContextMenu.showItem('AttrChange');
			dvContextMenu.showItem('ChangeTree');
			
			dvContextMenu.forEachItem(function(itemId){
				switch (itemId) {
				case 'CheckOut':
					dvContextMenu.hideItem('RequestChange');
					break;
				case 'MultiDownload':
					dvContextMenu.hideItem('RequestDownload');
					break;
				case 'Print':
					dvContextMenu.hideItem('RequestPrint');
					break;
				case 'Delete':
					dvContextMenu.hideItem('RequestDelete');
					break;
				default:
					break;
				}
			});
		}
		
		dvContextMenu.attachEvent("onClick", function(id){
			var eparentId = ('do-' + id).camelize();
			if (window[eparentId]) {
				window[eparentId](id);
			}
		})
		
		
		BodyLayOut = CreateBodyLayOut("4C");
		var tab_layout_width = BodyLayOut.cells('a').getWidth()-30;

		
		tree_layout = BodyLayOut.cells("a").attachLayout('3E');
		
		tree_layout.setSkin("dhx_web");
		tree_layout.cells('a').setWidth(tab_layout_width) ;
		tree_layout.cells('a').setHeight(BodyLayOut.cells('a').getHeight()*0.3);
		tree_layout.cells('b').setHeight(BodyLayOut.cells('b').getHeight()*0.7);
		tree_layout.cells('c').setHeight(BodyLayOut.cells('c').getHeight()*0.3);
		
		tree_layout.cells("a").setText($L('W_DOCKIND_NAME'));
		tree_layout.cells("b").setText($L('W_DATA_TREE'));
		tree_layout.cells("c").setText($L('W_MYTREE'));
		
		//tree_layout.cells('a').collapse();
		
		
		
		BodyLayOut.cells("b").fixSize(false,true);
		BodyLayOut.cells("b").attachObject('search');
		BodyLayOut.cells("b").setHeight(80);
		
		
		BodyLayOut.cells("c").attachObject('gridbox');
		BodyLayOut.cells("d").attachObject('page');
		
		
		MytreeBoxInit();
		mygridInit();
		//2021-11-17 Top tree & kind ID find
		var url = _contextPath + '/edms/tr/seTopTreeKindId.action';		
		new Ajax.Request(url, {
			method : 'POST',
			asynchronous : false,
			onSuccess : function(response) {
				var json = eval("(" + response.responseText + ")");
				treeTopId = json.treeTopId;
				kindTopId = json.kindTopId;
			}
		});
		
		kindBoxInit();
		treeBoxInit();

		var url = _contextPath + "/edms/tr/seExceptTreeList.action?";
		new Ajax.Request(
		    url, {
		        method: 'POST',
		        asynchronous: true,
		        onSuccess: function(response) {
		            var json = eval('(' + response.responseText + ')');
		            var exceptObj;
		            var exceptArray = new Array();
		            
		            json.forEach(function(e) {
		            		exceptObj = new Object();
		            	 	exceptObj.value = e.tid;
				            exceptObj.text = e.tname;
				            exceptArray.push(exceptObj);
                    });
		            exceptObj.options = exceptArray;
		         /*    myCombo = dhtmlXComboFromSelect("exceptTree");
		            myCombo.addOption(exceptArray);
		            j$('.dhxcombo_dhx_web').unwrap(); */

		            myCombo = new dhtmlXCombo("combo_zone", null, null, "checkbox");
		            
		            j$('.dhxcombo_input').css("height","32px");
		            j$('.dhxcombo_dhx_web').css("height","34px");
 		            j$('.dhxcombo_select_button').css({"height":"36px","top":"0px"});

 		            j$('.dhxcombo_select_button').width("35px");
 		            j$('.dhxcombo_dhx_web').css("display","inline-block");
 		           	j$('.dhxcombo_dhx_web').css("margin-top","-6px");
 		           	j$('.dhxcombo_select_img').css("background-color","#0A3A75");
 		           	j$('.dhxcombolist_dhx_web').css({"background-color":"#ffffff","border-color":"#9ecaed","box-sizing":"border-box","width":"200px"});
 		           
 		           
 		          	myCombo.setPlaceholder("분류 제외");
		            myCombo.setOptionWidth((j$('.topBtnArea').width()*0.09)*3);
		            myCombo.addOption(exceptArray);
		            
		            
		            myCombo.attachEvent("onCheck", function(value, state){
		            	exceptList = myCombo.getChecked();
		            	except_check = true;
					});
		            j$('.dhxcombolist_dhx_web').click(function(e){
		            	if(except_check == false)
		            	{
			            	let idx = myCombo.getSelectedIndex();
			                myCombo.setChecked(idx,!myCombo.isChecked(idx));
			                exceptList = myCombo.getChecked();
		            	}else{
		            		except_check = false;
		            	}
		            });
					j$('.dhxcombo_select_img').click(function(e){
		            });
					
					
					
		         /*    myCombo.attachEvent("onClose", function(){
		            	let idx = myCombo.getSelectedIndex();
		                myCombo.setChecked(idx,!myCombo.isChecked(idx));
		                exceptList = myCombo.getChecked();
		            }); */
		        }
		    });
		
		
		
		
		mygrid.enableSmartRendering(false);
		mygrid.enableDragAndDrop(true);
		//20211012 grid align
		//(확장자,종류,자효번호,자료명,rev,최종개정일,자료크기,tree_id,tree_name,dockind_id,dockind_name,개정사유,mr_code,etc,etc,공용도면,자료구분)
		//mygrid.setColAlign("center,center,left,center,left,right,right,left,left, left,left,center, center,center,center,center,center,center");
		toolbar.___attachInputObj("searchString",$('select_searchFormDTO_searchString'), doSearch);
		toolbar.___attachInputObj("itemSearchString",$('select_searchFormDTO_item'), doSearch);

		BodyLayOut.attachEvent("onResizeFinish", function(){
			BodyLayOut.cells("d").setHeight(40)
			mygrid.adjustColumnSize(0);
			tree_layout.cells('a').setHeight(BodyLayOut.cells('a').getHeight()*0.3);
			tree_layout.cells('b').setHeight(BodyLayOut.cells('b').getHeight()*0.7);
			tree_layout.cells('c').setHeight(BodyLayOut.cells('c').getHeight()*0.3);
			
			OpenInputBoxTooltip($("favorite_id"), "favorite_list");
			OpenInputBoxTooltip($("foreground_text_input"), "rec_search_list");
			
			 let combo_width = (j$('.topBtnArea').width()*0.09);
			
			/*  j$('.dhxcombo_input').css("height","32px");
	         j$('.dhxcombo_dhx_web').css("height","34px"); 
			 j$('.dhxcombo_dhx_web').css({"margin-left":"0px", "padding-left":"0px"});
			 j$('.dhxcombo_input').css({"margin-left":"0px", "padding-left":"20px"}); */
			 myCombo.setOptionWidth(combo_width*3);
			 //j$('.dhxcombolist_dhx_web').css("width",combo_width);
			 
			 
			 
			 
			
			j$(".historyBox").width(document.getElementById("foreground_text_input").clientWidth);
			j$(".favoriteBox").width(document.getElementById("favorite_id").clientWidth);
			
		});
		
		gridColumns10 = [
			{
				id : 'ext',
				label : '',
				width : 30,
				type : "coro",
				sort : "str",
				//filter : '#select_filter',
				CodeCombo : "extention"
			},
			/* {
				id : 'tree_id',
				label : '자료분류',
				width : 200,
				type : "corotxt",
				sort : "str",
				CodeCombo : "TREEID"
			}, */
			{
				id : 'manage_no',
				label : '관리번호',
				width : 180,
				type : "corotxt",
				hidden : true,
				sort : "str",
				CodeCombo : "TREEID"
			},
	      	{
	   			id : 'doc_no',
	   			label : '자료번호',
	   			width : 250,
	   			type : "rotxt",
	   			sort : "str"
	   		}, {
	   			id : 'doc_name',
	   			label : '도면명',
	   			width : 270,
	   			type : "rotxt",
	   			sort : "str"
	   		},
	   		{
	   			id : 'rev_no',
	   			label : 'rev',
	   			width : 40,
	   			type : "rotxt",
	   			sort : "str"
	   		}
	   	];
   		

   		var init10 = {
   			parent : 'gridbox10',
   			columns : gridColumns10,
   			enableMultiselect : true
   		};
   		checkoutgrid = makeGrid(init10);
   		checkoutgrid.setEditable(true);
   		checkoutgrid.attachEvent("onBeforeSorting", function(columnIndex, sortType, sortDirection) {
			return false;
		});
   		
   		sharedgridColumns = [ {
			id : 'ext',
			label : '',
			width : 30,
			type : "coro",
			sort : "str",
			CodeCombo : "extention"
		},   {
			id : 'doc_no',
			label : '자료번호',
			width : 200,
			type : "rotxt",
			sort : "str"
		}, {
			id : 'doc_name',
			label : '자료명',
			width : 200,
			type : "rotxt",
			sort : "str"
		}, {
			id : 'rev_no',
			label : 'Rev',
			width : 30,
			type : "rotxt",
			sort : "str"
		} ];

		var sharedinit = {
			parent : 'sharedgridbox',
			columns : sharedgridColumns,
			enableMultiselect : true
		};
		sharedgrid = makeGrid(sharedinit);
		
		//출력 팝업창 그리드  
		PrintPopUpGridColumns = [ {
			id : 'ext',
			label : '',
			width : 40,
			type : "coro",
			sort : "str",
			CodeCombo : "extention"
		},{
			id : 'doc_no',
			label : '자료번호',
			width : 130,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'doc_name',
			label : '자료명',
			width : 180,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'rev_no',
			label : 'Rev',
			width : 40,
			type : "rotxt",
			sort : "str",
			align : "center",
			dto : ''
		},{
			id : 'doc_seq',
			label : 'doc_seq',
			width : 50,
			type : "rotxt",
			sort : "str",
			hidden : true,
			align : "center",
			dto : ''
		},{
			id : 'tree_id',
			label : 'tree_id',
			width : 50,
			type : "rotxt",
			sort : "str",
			hidden : true,
			align : "center",
			dto : ''
		}];

		var PrintPopUpinit = {
			parent : 'PrintPopUpgridbox',
			columns : PrintPopUpGridColumns,
			enableMultiselect : true,
			drag : true
		};
		PrintPopUpgrid = makeGrid(PrintPopUpinit);
		PrintPopUpgrid.attachEvent("onBeforeSorting", function(columnIndex, sortType, sortDirection) {
			return false;
		});
		
		//다운로드 팝업창 그리드  
		DownloadPopUpGridColumns = [ {
			id : 'ext',
			label : '',
			width : 40,
			type : "coro",
			sort : "str",
			CodeCombo : "extention"
		},{
			id : 'doc_no',
			label : '자료번호',
			width : 130,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'doc_name',
			label : '자료명',
			width : 180,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'rev_no',
			label : 'Rev',
			width : 40,
			type : "rotxt",
			sort : "str",
			align : "center",
			dto : ''
		},{
			id : 'monochrome_yn',
			label : '흑백유무',
			width : 70,
			type : "corotxt",
			sort : "str",
			align : "center",
			DefVal : "Y",
			dto : ''
		},{
			id : 'pdf_yn',
			label : 'PDF유무',
			width : 70,
			type : "rotxt",
			sort : "str",
			hidden : true,
			align : "center",
			dto : ''
		},{
			id : 'doc_seq',
			label : 'doc_seq',
			width : 50,
			type : "rotxt",
			sort : "str",
			hidden : true,
			align : "center",
			dto : ''
		},{
			id : 'tree_id',
			label : 'tree_id',
			width : 50,
			type : "rotxt",
			sort : "str",
			hidden : true,
			align : "center",
			dto : ''
		}];

		var DownloadPopUpinit = {
			parent : 'DownloadPopUpgridbox',
			columns : DownloadPopUpGridColumns,
			enableMultiselect : true,
			drag : true
		};
		DownloadPopUpgrid = makeGrid(DownloadPopUpinit);
		DownloadPopUpgrid.attachEvent("onBeforeSorting", function(columnIndex, sortType, sortDirection) {
			return false;
		});
		
		
		var col = $G('DownloadPopUpgridbox').cellId("monochrome_yn");
		combo = DownloadPopUpgrid.getCombo(col);
		combo.put('Y', 'Y');
		combo.put('N', 'N');
		
		//출력요청 팝업창 그리드 
		ReqPrintGridColumns = [ {
			id : 'ext',
			label : '',
			width : 40,
			type : "coro",
			sort : "str",
			CodeCombo : "extention"
		},{
			id : 'doc_no',
			label : '자료번호',
			width : 130,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'doc_name',
			label : '자료명',
			width : 180,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'rev_no',
			label : 'Rev',
			width : 40,
			type : "rotxt",
			sort : "str",
			align : "center",
			dto : ''
		},{
			id : 'doc_seq',
			label : 'doc_seq',
			width : 50,
			type : "rotxt",
			sort : "str",
			hidden : true,
			align : "center",
			dto : ''
		}];

		var ReqPrintinit = {
			parent : 'ReqPrintGridDiv',
			columns : ReqPrintGridColumns,
			enableMultiselect : true,
			drag : true
		};
		RPgrid = makeGrid(ReqPrintinit);
		RPgrid.attachEvent("onBeforeSorting", function(columnIndex, sortType, sortDirection) {
			return false;
		});
		
		//다운로드요청 팝업창 그리드 
		ReqDownGridColumns = [ {
			id : 'ext',
			label : '',
			width : 40,
			type : "coro",
			sort : "str",
			CodeCombo : "extention"
		},{
			id : 'doc_no',
			label : '자료번호',
			width : 130,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'doc_name',
			label : '자료명',
			width : 180,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'rev_no',
			label : 'Rev',
			width : 40,
			type : "rotxt",
			sort : "str",
			align : "center",
			dto : ''
		},{
			id : 'doc_seq',
			label : 'doc_seq',
			width : 50,
			type : "rotxt",
			sort : "str",
			hidden : true,
			align : "center",
			dto : ''
		}];

		var ReqDowninit = {
			parent : 'ReqDownGridbox',
			columns : ReqDownGridColumns,
			enableMultiselect : true
		};
		RDgrid = makeGrid(ReqDowninit);
		RDgrid.attachEvent("onBeforeSorting", function(columnIndex, sortType, sortDirection) {
			return false;
		});
		
		//삭제요청 팝업창 그리드 
		ReqDeleteGridColumns = [ {
			id : 'ext',
			label : '',
			width : 40,
			type : "coro",
			sort : "str",
			CodeCombo : "extention"
		},{
			id : 'doc_no',
			label : '자료번호',
			width : 130,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'doc_name',
			label : '자료명',
			width : 180,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'rev_no',
			label : 'Rev',
			width : 40,
			type : "rotxt",
			sort : "str",
			align : "center",
			dto : ''
		},{
			id : 'doc_seq',
			label : 'doc_seq',
			width : 50,
			type : "rotxt",
			sort : "str",
			hidden : true,
			align : "center",
			dto : ''
		}];

		var ReqDeleteinit = {
			parent : 'ReqDeleteGridbox',
			columns : ReqDeleteGridColumns,
			enableMultiselect : true
		};
		RDELgrid = makeGrid(ReqDeleteinit);
		RDELgrid.attachEvent("onBeforeSorting", function(columnIndex, sortType, sortDirection) {
			return false;
		});
		
		//외주배포 팝업창 그리드
		OPgridColumns = [ {
			id : 'check',
			label : '#master_checkbox',
			width : 20,
			type : "ch",
			hidden : true,
			align : "center",
			sort : "str"
		},  {
			id : 'ext',
			label : '',
			width : 50,
			type : "coro",
			sort : "str",
			CodeCombo : "extention"
		},{
			id : 'doc_no',
			label : '자료번호',
			width : 150,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'doc_name',
			label : '자료명',
			width : 200,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'rev_no',
			label : 'Rev',
			width : 50,
			type : "rotxt",
			sort : "str",
			align : "center",
			dto : ''
		},{
			id : 'doc_seq',
			label : 'doc_seq',
			width : 50,
			type : "rotxt",
			sort : "str",
			hidden : true,
			align : "center",
			dto : ''
		}];

		var OPinit = {
			parent : 'OPgridbox',
			columns : OPgridColumns,
			enableMultiselect : true
		};
		OPgrid = makeGrid(OPinit);
		OPgrid.attachEvent("onBeforeSorting", function(columnIndex, sortType, sortDirection) {
			return false;
		});
		
		//수정요청 팝업창용 그리드
		RCgridColumns = [ {
			id : 'ext',
			label : '',
			width : 30,
			type : "coro",
			sort : "str",
			CodeCombo : "extention"
		},{
			id : 'manage_no',
			label : '관리번호',
			width : 120,
			type : "rotxt",
			sort : "str",
			align : "left",
			hidden : true,
			dto : ''
		},{
			id : 'doc_no',
			label : '자료번호',
			width : 120,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'doc_name',
			label : '자료명',
			width : 150,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'rev_no',
			label : 'Rev',
			width : 40,
			type : "rotxt",
			sort : "str",
			align : "center",
			dto : ''
		},{
			id : 'doc_seq',
			label : 'doc_seq',
			width : 50,
			type : "rotxt",
			sort : "str",
			hidden : true,
			align : "center",
			dto : ''
		},{
			id : 'markup_yn',
			label : '${t:s("W_MARKUP","마크업")}', 
			width : 195,
			type : "RCmarkup",
			sort : "str",
			align : "center",
			hidden : false,
			dto : 'searchFormDTO.documentDTO.markup_yn'
		},{
			id : 'attach_yn',
			label : '${t:s("W_ATTACH","첨부")}',
			width : 195,
			type : "RCfile",
			sort : "str",
			align : "center",
			dto : 'searchFormDTO.documentDTO.attach_yn'
		}];

		var ReqChangeinit = {
			parent : 'ReqChangegridbox',
			columns : RCgridColumns,
			enableMultiselect : false
		};
		RCgrid = makeGrid(ReqChangeinit);
		RCgrid.attachEvent("onBeforeSorting", function(columnIndex, sortType, sortDirection) {
			return false;
		});
		
		var attach_combo = RCgrid.getCombo($G('ReqChangegridbox').cellId('attach_yn'));
		attach_combo.put("Y","<img src='" + _contextPath+ "/img/ico/save.png'/>");
		attach_combo.put("N", "");
		
		
		var attachColumns = [  {
			id : 'check',
			label : '#master_checkbox',
			width : 50,
			type : "ch",
			hidden : true,
			sort : "str"
		}, {
			id : 'seq',
			label : '순번',
			hidden : true,
			width : 70,
			type : "rotxt",
			sort : "str"
		}, 
		{
			id : 'attach_name',
			label : '자료명',
			width : 200,
			type : "rotxt",
			sort : "str"
		}, 
		{
			id : 'file_path',
			label : '첨부파일 경로',
			width : 450,
			type : "rotxt",
			sort : "str"			
		} ,{
			id : 'doc_seq',
			label : 'doc_seq',
			width : 250,
			hidden : true,
			type : "rotxt",
			sort : "str"			
		} ];

		var init = {
			parent : "attachBox",
			columns : attachColumns,
			enableMultiselect : true
		};

		attachgridBox = makeGrid(init);
		
		//외주 수정요청 팝업창용 그리드
		ORCgridColumns = [ {
			id : 'ext',
			label : '',
			width : 30,
			type : "coro",
			sort : "str",
			CodeCombo : "extention"
		},{
			id : 'manage_no',
			label : '관리번호',
			width : 120,
			type : "rotxt",
			sort : "str",
			align : "left",
			hidden : true,
			dto : ''
		},{
			id : 'doc_no',
			label : '자료번호',
			width : 120,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'doc_name',
			label : '자료명',
			width : 150,
			type : "rotxt",
			sort : "str",
			align : "left",
			dto : ''
		},{
			id : 'rev_no',
			label : 'Rev',
			width : 40,
			type : "rotxt",
			sort : "str",
			align : "center",
			dto : ''
		},{
			id : 'doc_seq',
			label : 'doc_seq',
			width : 50,
			type : "rotxt",
			sort : "str",
			hidden : true,
			align : "center",
			dto : ''
		},{
			id : 'markup_yn',
			label : '${t:s("W_MARKUP","마크업")}', 
			width : 195,
			type : "RCmarkup",
			sort : "str",
			align : "center",
			hidden : false,
			dto : 'searchFormDTO.documentDTO.markup_yn'
		},{
			id : 'attach_yn',
			label : '${t:s("W_ATTACH","첨부")}',
			width : 195,
			type : "RCfile",
			sort : "str",
			align : "center",
			dto : 'searchFormDTO.documentDTO.attach_yn'
		}];

		var ReqChangeinit = {
			parent : 'OutReqChangegridbox',
			columns : ORCgridColumns,
			enableMultiselect : false
		};
		ORCgrid = makeGrid(ReqChangeinit);
		ORCgrid.attachEvent("onBeforeSorting", function(columnIndex, sortType, sortDirection) {
			return false;
		});
		
		var attach_combo2 = ORCgrid.getCombo($G('OutReqChangegridbox').cellId('attach_yn'));
		attach_combo2.put("Y", "<img src='" + _contextPath+ "/img/ico/save.png'/>");
		attach_combo2.put("N", "");
		
		var outAttachColumns = [  {
			id : 'check',
			label : '#master_checkbox',
			width : 50,
			type : "ch",
			hidden : true,
			sort : "str"
		}, {
			id : 'seq',
			label : '순번',
			hidden : true,
			width : 70,
			type : "rotxt",
			sort : "str"
		}, 
		{
			id : 'attach_name',
			label : '자료명',
			width : 200,
			type : "rotxt",
			sort : "str"
		}, 
		{
			id : 'file_path',
			label : '첨부파일 경로',
			width : 450,
			type : "rotxt",
			sort : "str"			
		} ,{
			id : 'doc_seq',
			label : 'doc_seq',
			width : 250,
			hidden : true,
			type : "rotxt",
			sort : "str"			
		} ];

		var init = {
			parent : "outAttachBox",
			columns : outAttachColumns,
			enableMultiselect : true
		};

		outAttachgridBox = makeGrid(init);
   		
		
		tagInfoCol = [{
			id : 'check',
			label : '#master_checkbox',
			width : 40,
			type : "ch",
			align : "center",
			sort : "str"
		},  {
			id : 'item',
			label : '* ${t:s("W_DATA_PARTCODE","Tag")}',
			width : 320,
			type : "edtxt",
			align : "left",
			sort : "str",
			bNull : false,
			dto : 'itemDTO.item'
		}, {
			id : 'oldItem',
			width : 60,
			type : "rotxt",
			dto : 'itemDTO.oldItem',
			hidden : true
		}, {
			id : 'doc_seq',
			width : 60,
			type : "ron",
			sort : "str",
			hidden : true,
			dto : 'itemDTO.doc_seq'
		}];
		
		var tagInfoinit = {
			parent : 'tagInfogridbox',
			columns : tagInfoCol,
			DTOListName : "itemDTOList"
		};
		tagInfoGrid = makeGrid(tagInfoinit);
		tagInfoGrid.enableSmartRendering(false);
		
		tagInfoGrid.attachEvent("onEditCell", function(stage, rId, cInd, nVal, oVal) {
			var cId = this.getColumnId(cInd);
			var rInd = this.getRowIndex(rId);
			
			if(cId != "item") return true;
			if(stage != 2) return true;
			if(nVal == oVal) return true;
			
			for(var i=0; i<this.getRowsNum(); i++) {
				var id = this.getRowId(i);
				var otherVal = $G('tagInfogridbox').cells(id, cId).getValue();
				
				if(rInd != i && nVal == otherVal) {
					alert($L('SN_CHK_TAGNAME') + " " + (i-0+1) + "행, Tag: " + nVal);
					$G('tagInfogridbox').cells(rId, 'check').setValue(0);
					return false;
				}
			}
			
			return true;
			
		});
		
		//한화 외주배포 팝업
		gridColumnsHanwha = [{
				id : 'project_doc_no',
				label : '관리번호',
				width : 120,
				type : "rotxt",
				align : "center",
				sort : "str",
				dto : 'distributeMngDTO.project_doc_no'
			},{
				id : 'documentDTO.doc_seq',
				label : 'doc_seq',
				width : 120,
				type : "rotxt",
				align : "center",
				hidden : true,
				sort : "str",
				dto : 'distributeMngDTO.documentDTO.doc_seq'
			},{
				id : 'documentDTO.file_id',
				label : 'file_id',
				width : 120,
				type : "rotxt",
				align : "center",
				hidden : true,
				sort : "str",
				dto : 'distributeMngDTO.documentDTO.file_id'
			},{
				id : 'documentDTO.doc_no',
				label : '자료번호',
				width : 100,
				type : "rotxt",
				align : "center",
				sort : "str",
				dto : 'distributeMngDTO.documentDTO.doc_no'
			},{
				id : 'documentDTO.doc_name',
				label : '자료명',
				width : 100,
				type : "rotxt",
				align : "center",
				sort : "str",
				dto : 'distributeMngDTO.documentDTO.doc_name'
			},{
				id : 'documentDTO.rev_no',
				label : 'Rev',
				width : 40,
				type : "rotxt",
				align : "center",
				sort : "str",
				dto : 'distributeMngDTO.documentDTO.rev_no'
			},{
				id : 'documentDTO.tree_id',
				label : '자료분류',
				width : 150,
				type : "rotxt",
				align : "center",
				hidden : true,
				sort : "str",
				dto : 'distributeMngDTO.documentDTO.tree_id'
			},{
				id : 'documentDTO.tree_path',
				label : '자료분류',
				width : 120,
				type : "corotxt",
				//codecombo : "TREEID",
				align : "center",
				sort : "str",
				dto : 'distributeMngDTO.documentDTO.tree_path'
			},{
				id : 'documentDTO.dockind_id',
				label : 'Area',
				width : 200,
				type : "rotxt",
				align : "center",
				sort : "str",
				hidden : true,
				dto : 'distributeMngDTO.documentDTO.dockind_id'
			},{
				id : 'documentDTO.dockind_path',
				label : 'Area',
				width : 120,
				type : "corotxt",
				codecombo : "DOCKIND",
				align : "center",
				sort : "str",
				dto : 'distributeMngDTO.documentDTO.dockind_path'
			},{
				id : 'distribute_enddate',
				label : '만료일자',
				width : 80,
				type : "dhxCalendar",
				align : "center",
				sort : "date",
				dto : 'distributeMngDTO.distribute_enddate'
			},{
				id : 'download_cnt',
				label : '다운로드 횟수',
				width : 80,
				type : "edtxt",
				align : "center",
				sort : "str",
				dto : 'distributeMngDTO.download_cnt'
			},{
				id : 'print_cnt',
				label : '출력횟수',
				width : 80,
				type : "edtxt",
				align : "center",
				sort : "str",
				dto : 'distributeMngDTO.print_cnt'
			},{
				id : 'company_id',
				label : '업체명',
				width : 150,
				type : "rotxt",
				align : "center",
				hidden : true,
				sort : "str",
				dto : 'distributeMngDTO.company_id'
			},{
				id : 'company_name',
				label : '업체명',
				width : 150,
				type : "company",
				//type : "rotxt",
				align : "center",
				sort : "str",
				dto : 'distributeMngDTO.company_name'
			}
	   	];
   		var initHanwha = {
   			parent : 'OutDistributeReqGridbox',
   			columns : gridColumnsHanwha,
   			enableMultiselect : true
   		};
   		OutDistributeReqGrid = makeGrid(initHanwha);
   		OutDistributeReqGrid.setEditable(true);
   		
   		markupColumns = [
			{
				id : 'markupseq',
				label : '마크업 번호',
				width : 100,
				align : "center",
				type : "rotxt",
				sort : "str"
			},
			{
				id : 'emp_id',
				label : '사용자',
				width : 180,
				align : "center",
				type : "rotxt",
				sort : "str"
			},
	      	{
	   			id : 'ver',
	   			label : 'Ver.',
	   			width : 80,
	   			align : "center",
	   			type : "rotxt",
	   			sort : "str"
	   		}, {
	   			id : 'create_date',
	   			label : '생성일',
	   			width : 270,
	   			align : "center",
	   			type : "rotxt",
	   			sort : "str"
	   		}, {
	   			id : 'doc_seq',
	   			label : 'doc_seq',
	   			width : 270,
	   			align : "center",
	   			hidden : true,
	   			type : "rotxt",
	   			sort : "str"
	   		}, {
	   			id : 'markuppreview',
	   			label : '미리보기',
	   			width : 120,
	   			align : "center",
	   			type : "mkpreview",
	   			sort : "str"
	   		}
	   	];
   		

   		var markupinit = {
   			parent : 'markupgridbox',
   			columns : markupColumns
   		};
   		
   		markupgrid = makeGrid(markupinit);
   		
   		
   		/**
   		author : choihj
   		date : 21-07-08
   		comment : Search Grid Personal work(make grid)
   		*/
   		GridSettingColumns = [
			{
	   			id : 'buse',
	   			label : '보기 여부',
	   			width : 80,
	   			align : "center",
	   			type : "ch",
	   			sort : "str"
	   		},
			{
				id : 'label',
				label : '컬럼 명칭',
				width : 250,
				align : "center",
				type : "rotxt",
				sort : "str"
			},{
				id : 'id',
				hidden : true,
				sort : "str"
			},
			{
				id : 'width',
				label : '컬럼 크기',
				width : 70,
				align : "center",
				type : "edtxt",
				sort : "str",
				hidden : true
			}
	   	];
   		

   		var GridSettingInit = {
   			parent : 'GridSettingBox',
   			enableMultiselect : true,
   			columns : GridSettingColumns
   		};
   		GridSetting = makeGrid(GridSettingInit);
   		GridSetting.enableDragAndDrop(true);
   		
   		gridSetting("SS","N");
   		
   		//end -> make grid
   		
   		//end
   		var win = {
			searchDiv : {
				id : 'searchDiv',
				x : 250,
				y : 40,
				width : 600,
				height : 420,
				name : $L("W_DETAIL_SEARCH"),
				centerShow : true
			},
			ReqPrintDiv : {
				id : 'ReqPrintDiv',
				x : 20,
				y : 30,
				width : 420,
				height : 515,
				name : $L("W_PLOT_REQ"),
				modal : true,
				centerShow : true
			},
			ReqChangeDiv : {
				id : 'ReqChangeDiv',
				x : 20,
				y : 30,
				width : 750,
				height : 550,
				name : $L("W_DATA_MODIFY_REQ"),
				modal : true,
				centerShow : true
			},
			ReqDeleteDiv : {
				id : 'ReqDeleteDiv',
				x : 20,
				y : 30,
				width : 470,
				height : 460,
				name : $L("W_DATA_DELETE_REQ"),
				modal : true,
				centerShow : true
			},
			OutReqChangeDiv : {
				id : 'OutReqChangeDiv',
				x : 20,
				y : 30,
				width : 750,
				height : 660,
				name : $L("W_OUT_DATA_MODIFY_REQ"),
				modal : true,
				centerShow : true
			},
			checkOutDiv : {
				id : 'checkOutDiv',
				x : 20,
				y : 30,
				width : 640,
				height : 560,
				name : $L("W_CHECKOUT"),
				modal : true,
				centerShow : true
			},
			downloadDiv : {
				id : 'downloadDiv',
				x : 20,
				y : 30,
				width : 500,
				height : 760,
				name : $L("W_DOWNLOAD"),
				centerShow : true,
				modal : true
			},
			downloadRequestDiv : {
				id : 'downloadRequestDiv',
				x : 20,
				y : 30,
				width : 420,
				height : 470,
				name : $L("W_DOWNLOAD_REQ"),
				centerShow : true
			},			
			ChangeTreeDiv : {
				id : 'ChangeTreeDiv',
				x : 20,
				y : 30,
				width : 580,  	//2014.2.14 수정
				height : 250, 	//2014.2.14 수정
				name : $L("W_CHANGE_TREE_HD"),
				modal : true,
				centerShow : true
			},
			docInfoDiv : {
				id : 'docInfoDiv',
				x : 20,
				y : 30,
				width : 1140,
				height : 650,
				name : $L("W_DATA_INFO"),
				modal : true,
				centerShow : true,
				attachToolbar : {
					Align : 'left',
					IconsPath : _contextPath + "/img/ico/",
					Buttons : [ ]  // Buttons 수정 2014.2.4 
				}
			},
			/**
			2014.06.11
			WooChul Jung
			favorite add button
			*/
			favoriteAddDiv : {
				id : 'favoriteAddDiv',
				x : 20,
				y : 30,
				width : 450,
				height : 280,
				name : $L("W_FAVORITE_ADD"),
				modal : true,
				centerShow : true
			},
			/**
			2014.05.30
			WooChul Jung
			favorite remove button
			*/
			favoriteRemoveDiv : {
				id : 'favoriteRemoveDiv',
				x : 20,
				y : 30,
				width : 450,
				height : 280,
				name : $L("W_FAVORITE_REMOVE"),
				modal : true,
				centerShow : true
			},
			itemFileUploadDiv : {
				id : 'itemFileUploadDiv',
				x : 20,
				y : 30,
				width : 400,
				height : 120,
				name : $L("W_EXCEL_INSERT"),
				modal : true,
				centerShow : true
			},
			fileUploadDiv : {
				id : 'fileUploadDiv',
				x : 20,
				y : 30,
				width : 400,
				height : 120,
				name : $L("W_EXCEL_INSERT"),
				modal : true,
				centerShow : true
			},
			//end
			
			PrintPopUpDiv2 : {
				id : 'PrintPopUpDiv2',
				x : 20,
				y : 30,
				width : 450,
				height : 560,
				name : "자료 출력",
				modal : true,
				centerShow : true
			},
			//end
			// 2016.10.25 Kijoo Kim. 태그 추가 팝업
			tagInfoDiv : {
				id : 'tagInfoDiv',
				x : 250,
				y : 40,
				width : 400,
				height : 610,
				name : $L("W_DATA_PARTCODE"),
				modal : true,
				centerShow : true,
				attachToolbar : {
					Align : 'left',
					IconsPath : _contextPath + "/img/ico/",
					Buttons : [ {
						id : 'add',
						position : 2,
						text : $L('W_ADD', '추가'),
						img : 'add.png',
						d_img : 'add.png'
					}, {
						id : 'save',
						position : 3,
						text : $L('W_SAVE', '저장'),
						img : 'save.png',
						d_img : 'save.png'
					}, {
						id : 'delete',
						position : 4,
						text : $L('W_DEL', '삭제'),
						img : 'delete.png',
						d_img : 'delete.png'
					} ]
				}
			}
			,
			SharedListDiv : {
				id : 'SharedListDiv',
				x : 20,
				y : 30,
				width : 500, 
				height : 460,
				name : $L("W_SHARED_FOLDER_LIST"),
				modal : true,
				centerShow : true
			},
			OutPublishDiv : {
				id : 'OutPublishDiv',
				x : 20,
				y : 30,
				width : 500, //add20160105 출력요청 기본값 420에서 720으로 변경
				height : 710,
				name : '외주배포',
				modal : true,
				centerShow : true
			},
			ReqChangeDivAttachList : {
				id : 'ReqChangeDivAttachList',
				x : 20,
				y : 30,
				width : 670,
				height : 360,
				name : $L("W_ATTACH"),
				centerShow : true,
				modal : true,
				attachToolbar : {
					Align : 'left',
					IconsPath : _contextPath + "/img/ico/",
					Buttons : [ 
		            {
						id : 'save',
						position : 0,
						text : $L('W_SAVE', '저장'),
						img : 'save.png',
						d_img : 'save.png'
					},{
						id : 'add',
						position : 1,
						text : $L('W_ADD', '추가'),
						img : 'add.png',
						d_img : 'add.png'
					}, {
						id : 'delete',
						position : 2,
						text : $L('W_DEL', '삭제'),
						img : 'delete.png',
						d_img : 'delete.png'
					}]
				// Buttons 수정 2014.2.4 
				}
			},
			outReqChangeDivAttachList : {
				id : 'outReqChangeDivAttachList',
				x : 20,
				y : 30,
				width : 670,
				height : 360,
				name : $L("W_ATTACH"),
				centerShow : true,
				modal : true,
				attachToolbar : {
					Align : 'left',
					IconsPath : _contextPath + "/img/ico/",
					Buttons : [ 
		            {
						id : 'save',
						position : 0,
						text : $L('W_SAVE', '저장'),
						img : 'save.png',
						d_img : 'save.png'
					},{
						id : 'add',
						position : 1,
						text : $L('W_ADD', '추가'),
						img : 'add.png',
						d_img : 'add.png'
					}, {
						id : 'delete',
						position : 2,
						text : $L('W_DEL', '삭제'),
						img : 'delete.png',
						d_img : 'delete.png'
					}]
				// Buttons 수정 2014.2.4 
				}
			},
			attrChangeDiv : {
				id : 'attrChangeDiv',
				x : 20,
				y : 30,
				width : 500, 
				height : 200,
				name : $L("W_ATTR_CHANGE"),
				modal : true,
				centerShow : true
			},
			OutDistributeReqDiv : { //한화 외주배포
				id : 'OutDistributeReqDiv',
				x : 20,
				y : 30,
				width : 1100, 
				height : 500,
				name : '외주배포',
				modal : true,
				centerShow : true
			},  CompantInfoSelectInfoDiv : { //한화 업체 조회 창
				id : 'CompantInfoSelectInfoDiv',
				x : 20,
				y : 30,
				width : 300, 
				height : 500,
				name : '업체 조회',
				modal : true,
				centerShow : true
			}, DragTreeChangeDiv : { //Drag&Drop 분류변경
				id : 'DragTreeChangeDiv',
				x : 20,
				y : 30,
				width : 400, 
				height : 100,
				name : '분류변경',
				modal : true,
				centerShow : true
			}, createTreeDiv : { //분류생성
				id : 'createTreeDiv',
				x : 20,
				y : 30,
				width : 400, 
				height : 200,
				name : '분류추가',
				modal : true,
				centerShow : true
			}, markupListDiv : { //분류생성
				id : 'markupListDiv',
				x : 20,
				y : 30,
				width : 800, 
				height : 500,
				name : '마크업리스트',
				modal : true,
				centerShow : true
			}
			
	   		/**
	   		author : choihj
	   		date : 21-07-08
	   		comment : Search Grid Personal work(make grid)
	   		*/
	   		,
	   		GridSettingDiv : {
	   			id : 'GridSettingDiv',
				x : 20,
				y : 30,
				width : 450, 
				height : 600,
				name : '컬럼 배열',
				modal : true,
				centerShow : true
	   		},
	   		CalendarDiv : {
	   			id : 'CalendarDiv',
				x : 20,
				y : 30,
				width : 300, 
				height : 180,
				name : '기간 맞춤설정',
				modal : true,
				centerShow : true
	   		},
	   		ExcelDiv : {
	   			id : 'ExcelDiv',
				x : 20,
				y : 30,
				width : 300, 
				height : 230,
				name : 'Excel 다운로드',
				modal : true,
				centerShow : true
	   		},
	   		progressDiv : {
				id : 'progressDiv',
				x : 20,
				y : 30,
				width : 510,
				height : 120,
				name : '',
				modal : true,
				centerShow : true
			}
		}; 
		buttonAdd(win); // Buttons관련 추가 2014.2.4  OutDistributeReqDiv
		PopWin = new PopWindows(win);
		

		addSearchDataList();
		dndAllRevChk();
		attrChngAllRevChk();
		cssCoustom();
		chngTreeAllRevChk();
		
		
		
		//statistics sort start
		
		//조회건수 초기 값 설정
		if($('select_searchFormDTO_searchString').value != "")
		{
			document.getElementById("foreground_text_input").value = $('select_searchFormDTO_searchString').value;
			doSearch();
		} 
		seMyPreference("SC");
		addFavoriteList();
		j$('#Search_tool').css("display", "block");
		
		var CalendarData = [
			{type: "settings", position: "label-left", labelWidth: 120, inputWidth: 145},
			{type: "calendar", name: "start_date", label: "시작일", value: "", dateFormat: "%Y-%m-%d", enableTime: true, calendarPosition: "right"},
			{type: "calendar", name: "end_date", label: "종료일", value: "", dateFormat: "%Y-%m-%d", enableTime: true, calendarPosition: "right"}
		];
		CalendarForm = new dhtmlXForm("Calendar", CalendarData);
		
		CalendarForm.attachEvent("onChange", function(a,b){
			if(a == "start_date")
			{
				$('select_searchFormDTO_startDate').value = CalendarForm._formLS.start_date.input.value;
			}else if(a == "end_date")
			{
				$('select_searchFormDTO_endDate').value = CalendarForm._formLS.end_date.input.value;
			}
		});
		
		j$("#number_of_view").on("change", function(ev) {
			inMyPreference(j$(this).val(),"SC");
		});
	 	GridSetting.attachEvent("onDrop", function(sId,tId,dId,sObj,tObj,sCol,tCol){
			inMyPreference(gridChanged("SS"), "SS");
			mygridInit();
		}); 
	    GridSetting.attachEvent("onCheck", function(rId,cInd,nValue){
	 		inMyPreference(gridChanged("SS"), "SS");
	 		mygridInit();
	 	}); 
	 	 
		//historybox start
		j$(".historyBox").width(document.getElementById("foreground_text_input").clientWidth);
		j$("#favorite_id").width(document.getElementById("favorite_id").clientWidth*3);
		j$(".favoriteBox").width(document.getElementById("favorite_id").clientWidth);
		
		/* j$("article").css("color","white");
		j$("article").css("background-color","#99cc00"); */
		
		OpenInputBoxTooltip($("favorite_id"), "favorite_list");
		OpenInputBoxTooltip($("foreground_text_input"), "rec_search_list");
		
		j$("#favorite_id").on("propertychange change keyup paste input", function(ev) {
			if(ev.which == 27)
			{
				j$('#favorite_list').css("display", "none");
			}
		});
		j$("#foreground_text_input").on("propertychange change keyup paste input", function(ev) {
		    //li keydown
		    var current = j$(".selected");
		    if (ev.which == 38) {
		        // UP KEY
		        if(j$('#rec_search_list').css("display") == "none")
	    		{
		    		j$('#rec_search_list').css("display", "block");
	    		}
		        var prev = current.prev("li");
		        if (prev.length) {
		            current.removeClass("selected");
		            prev.addClass("selected");
		            
		            if(current.next("li").css("display") == "none")
		        	{
		        		while(current.prev("li").css("display")=="none")
		        		{
		        			current = j$(".selected");
		        			prev = current.prev("li");
		        			current.removeClass("selected");
		        			prev.addClass("selected");
		        		}
		        	}
		        } else {
		            j$('#rec_search_list').css("display", "block");
		            current.removeClass("selected");
		            j$("#dropdown li").last().addClass("selected")
		        }
		        if(j$(".selected").css("display") != "none")
		        {
		        	document.getElementById("foreground_text_input").value = j$(".selected").text().replace(j$(".selected").find('span:eq(-1)').text(), "");
		        } else
	        	{
		        	 j$('#rec_search_list').css("display", "block");
			            current.removeClass("selected");
			            j$("#dropdown li").first().addClass("selected");
			            document.getElementById("foreground_text_input").value = j$(".selected").text().replace(j$(".selected").find('span:eq(-1)').text(), "");
		        	}
		        
		    } else if (ev.which == 40) {
		        // DOWN KEY
		        if(j$('#rec_search_list').css("display") == "none")
	    		{
		    		j$('#rec_search_list').css("display", "block");
	    		}
		        var next = current.next("li");
		        if (next.length) {
		        	current.removeClass("selected");
		            next.addClass("selected");
		        	if(current.next("li").css("display") == "none")
		        	{
		        		while(current.next("li").css("display")=="none")
		        		{
		        			current = j$(".selected");
		        			next = current.next("li");
		        			current.removeClass("selected");
				            next.addClass("selected");
		        		}
		        	}
		        } 
	        	else {
		            j$('#rec_search_list').css("display", "block");
		            current.removeClass("selected");
		            j$("#dropdown li").first().addClass("selected");
		        }
		        if(j$(".selected").css("display") != "none")
			        document.getElementById("foreground_text_input").value = j$(".selected").text().replace(j$(".selected").find('span:eq(-1)').text(), "");
		        else
	        	{
	        	 	j$('#rec_search_list').css("display", "block");
		            current.removeClass("selected");
		            j$("#dropdown li").first().addClass("selected");
		            document.getElementById("foreground_text_input").value = j$(".selected").text().replace(j$(".selected").find('span:eq(-1)').text(), "");
	        	}
		    } else if (ev.which == 13) {
		        var str;
		        if (j$(".selected").text() == "" && j$(this).val() != j$(".selected").text().replace(j$(".selected").find('span:eq(-1)').text(), "")) {
		            str = j$(this).val();
		        } 
		        else {
		            str = j$(".selected").text().replace(j$(".selected").find('span:eq(-1)').text(), "");
		        }
		        selectString(str, "K");
		    } else if (ev.which == 27) {
		        j$('#rec_search_list').css("display", "none");
		        document.getElementById("foreground_text_input").value = "";
		    } else {
		        j$('#rec_search_list').css("display", "block");
		        //addSearchDataList(j$(this).val());
		        j$("#dropdown li").filter(function() {
		            j$(this).toggle(j$(this).text().toLowerCase().indexOf(j$("#foreground_text_input").val().toLowerCase()) > -1);
		        });

		        j$("#dropdown li p").unmark();
		        j$("#dropdown li p").mark(j$("#foreground_text_input").val());
		    }
		    //li keydown end
		});

		j$(document).mouseup(function(e) {
		    var LayerPopup = j$(".historyBox");
		    //if (LayerPopup.has(e.target).length == 0) {
		    	if(LayerPopup.has(e.target).length == 0 && j$(".favoriteBox").has(e.target).length == 0){
		    	/** 검색 이력 */
		    	//다른 영역 선택
		        document.getElementById('rec_search_list').style.display = "none";
		        document.getElementById('favorite_list').style.display = "none";
		        
		        if(j$("#foreground_text_input").val() != "")
		    	{
			        j$("#dropdown li").filter(function() {
			            j$(this).toggle(j$(this).text().toLowerCase().indexOf(j$("#foreground_text_input").val().toLowerCase()) > -1);
		          	});
		        	j$("#dropdown li p").unmark();
		        	j$("#dropdown li p").mark(j$("#foreground_text_input").val());
		    	}
		    }
		    else {
		        if (e.target.name == "historyDelete") {
		            deleteSearchWord(e.target.id,"Search");
		        }else if(e.target.name == "favoriteDelete")
		        {
		        	deleteSearchWord(e.target.id,"Favorite");
		        }
		        else {
		            if (e.target.nodeName != "B")
	            	{
		            	if(j$('#rec_search_list').css("display") == "block")
		            	{
	                		selectString(e.target,"M");
	                	}else if(j$('#favorite_list').css("display") == "block")
                		{
	                		selectString(e.target,"F",e.target.id);
                		}
		            	
	            	}
		        }
		    }
		});
		//historybox end
		
		
		$('select_searchFormDTO_documentDTO_tree_id').value = treeTopId;
		$('select_searchFormDTO_treeDTO_tname').value = mytree.getItemText(treeTopId);
		$('select_searchFormDTO_documentDTO_dockind_id').value = kindTopId;
		
		// grid columns 선택 후 ctrl + c (복사) 이벤트 처리
		var agent = navigator.userAgent.toLowerCase();
		if ( (navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
			
		}else{
			 var mygrid_clip = mygrid._clip_area = document.createElement("textarea");
			 mygrid_clip.className = "dhx_tab_ignore";
			 mygrid_clip.style.cssText = "position:absolute; width:1px; height:1px; overflow:hidden; color:transparent; background-color:transparent; bottom:1px; right:1px; border:none;";
		}
		// grid columns 선택 후 ctrl + c (복사) 이벤트 처리
		document.body.addEventListener("keydown", function (ev) {
		    ev = ev || window.event;  
		    var key = ev.which || ev.keyCode; 
		      
		    var ctrl = ev.ctrlKey ? ev.ctrlKey : ((key === 17)
		        ? true : false);
		  
		    if (key == 67 && ctrl) {
		    	
		    	if(grid_select_rid != "" && grid_select_cind !=""){
		    		// grid clipboard 복사
			        mygrid.cellToClipboard(grid_select_rid,grid_select_cind);
		    	}
		    }
		    
		    if (key == 86 && ctrl) {
		    	var agent = navigator.userAgent.toLowerCase();
				if ( (navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
					
				}else{
					if(mygrid._clip_area.value != null && mygrid._clip_area.value != ""){
			    		window.setTimeout(function () {
				    		ev.path[0].value=mygrid._clip_area.value
				    		mygrid._clip_area.value = null
				    		grid_select_rid = '';
				    		grid_select_cind = '';
				        }, 1)	
			    	}
				}
		    }
		    	
		},false);
	}
	
	var PopWin = null;
	var empPopup = null;
	function cssCoustom(){
		j$("div.hdrcell").css("padding-left","0px");
	}

	
	
	// 메세지 바이트 체크
	function chkMsgLength(intMax, objMsg, st) {

		var length = lengthMsg(objMsg.value);

		st.innerHTML = length;//현재 byte수를 넣는다

		if (length > intMax) {
			alert("메세지는 40글자 이상이므로 초과된 글자수는 자동으로 삭제됩니다.\n");
			objMsg.value = objMsg.value.replace(/\r\n$/, "");
			objMsg.value = assertMsg(intMax, objMsg.value, st);
		}
	}
	// 현재 메시지 바이트 수 계산
	function lengthMsg(objMsg) {

		var nbytes = 0;
		for (i = 0; i < objMsg.length; i++) {
			var ch = objMsg.charAt(i);
			if (escape(ch).length > 4) {
				nbytes += 2;
			} else if (ch == '\n') {
				if (objMsg.charAt(i - 1) != '\r') {
					nbytes += 1;
				}
			} else if (ch == '<' || ch == '>') {
				nbytes += 4;
			} else {
				nbytes += 1;
			}
		}
		return nbytes;
	}

	// 80 바이트 넘는 문자열 자르기
	function assertMsg(intMax, objMsg, st) {
		var inc = 0;
		var nbytes = 0;
		var msg = "";

		var msglen = objMsg.length;

		for (i = 0; i < msglen; i++) {
			var ch = objMsg.charAt(i);

			if (escape(ch).length > 4) {
				inc = 2;
			} else if (ch == '\n') {
				if (objMsg.charAt(i - 1) != '\r') {
					inc = 1;
				}
			} else if (ch == '<' || ch == '>') {
				inc = 4;
			} else {
				inc = 1;
			}

			if ((nbytes + inc) > intMax) {
				break;
			}

			nbytes += inc;
			msg += ch;
		}

		st.innerHTML = nbytes; //현재 byte수를 넣는다

		return msg;
	}
	
	// 목록보기 & 이미지보기 변경 
	function imgClick(id) {
		var winsize = document.documentElement.clientWidth;
		if (j$("#search_category option:selected").val() == "preview_change") {
			buttonCheck = 2;
			BodyLayOut.cells("c").detachObject('gridbox');
			BodyLayOut.cells("d").detachObject('page');
			
			BodyLayOut.cells("d").attachObject('paging_here');

			myDataView = BodyLayOut.cells("c").attachDataView({
				type : {
					template : "html->doc_list_template",
					padding : 5,
					height : 150,
					width : (winsize-400) / 2
				},
				tooltip:{
					template:function(id){
						if(dataviewImg){
							var width = myDataView._obj.clientWidth/2;
							var height = myDataView._obj.clientHeight;
							return "<img src=/edmsimages/il/"+id.img+".png width='"+width+"' height='"+height+"' class='gray' onError='this.width=0; this.height=0;'>"	
						}else{
							return "";
						}
					}
				},
				 pager:{
					container:"paging_here",
					size:20
				},
				drag : true,
				select : "multiselect"

			});
			// context menu 열기 전 Event
			myDataView.attachEvent("onBeforeContextMenu",function(id,e){
				
				// 문서분류 권한 분기 필요 권한일 경우
				if(_dwgAdminCheck){
					var selectedId = myDataView.getSelected();
					var tree_auth_check = 0;
					var treeCheckAuthCnt = 0;
					var factory_code = _factory_code;	

					// _ses.factory_code : code에 등록된 factory 코드의 tid를 가지고 full_path 얻어옴
					// 여러분류가 가능 하므로 path1 | path2 | path3 형식의 String -> 트리관리에서 tname에 '|' 입력 방지 
					// 자료 선택 시, 선택한 자료의 tree_path를 가져와 _ses.factory_code에 입력된 path와 비교하여 메뉴 분기
					try {
						selectedId.size();
						for(var i=0; i< selectedId.size(); i++){
							
							var sel_tree_text = myDataView.get(selectedId[i]).tree_path;
							
							if(factory_code == '0'){
								tree_auth_check = 1;
							}else{
								 
								factory_code_arr = factory_code.split('|');
								for(var k = 0; k < factory_code_arr.size(); k++){
									if(sel_tree_text.indexOf(factory_code_arr[k]) > -1){
										treeCheckAuthCnt++;
									}
								}
								
							}
							
							if(treeCheckAuthCnt > 0){
								tree_auth_check = 0;
								treeCheckAuthCnt = 0;
							}else{
								tree_auth_check = 1;
								treeCheckAuthCnt = 0;
								break;
							}
						}
						
					} catch (e) {
						// TODO: handle exception
						var sel_tree_text = myDataView.get(selectedId).tree_path;
						if(sel_tree_text.indexOf(_factory_code)>-1){
							tree_auth_check = 0;
						}else{
							tree_auth_check = 1;
						}
					}
					
					if(tree_auth_check == 1){
						dvContextMenu.showItem('RequestChange');
						dvContextMenu.showItem('RequestDelete');
						dvContextMenu.showItem('RequestDownload');
						dvContextMenu.showItem('RequestPrint');
						
						dvContextMenu.hideItem('CheckOut');
						dvContextMenu.hideItem('Delete');
						dvContextMenu.hideItem('MultiDownload');
						dvContextMenu.hideItem('Print');
						dvContextMenu.hideItem('AttrChange');
						dvContextMenu.hideItem('ChangeTree');
					}else{
						dvContextMenu.showItem('RequestChange');
						dvContextMenu.showItem('RequestDelete');
						dvContextMenu.showItem('RequestDownload');
						dvContextMenu.showItem('RequestPrint');
						
						dvContextMenu.showItem('CheckOut');
						dvContextMenu.showItem('Delete');
						dvContextMenu.showItem('MultiDownload');
						dvContextMenu.showItem('Print');
						dvContextMenu.showItem('AttrChange');
						dvContextMenu.showItem('ChangeTree');
						
						// 요청과 쌍으로 이뤄진 기능 체크하여 권한이 이미 없을 땐 요청을 띄워주도록 변경
						dvContextMenu.forEachItem(function(itemId){
							switch (itemId) {
							case 'CheckOut':
								dvContextMenu.hideItem('RequestChange');
								break;
							case 'MultiDownload':
								dvContextMenu.hideItem('RequestDownload');
								break;
							case 'Print':
								dvContextMenu.hideItem('RequestPrint');
								break;
							case 'Delete':
								dvContextMenu.hideItem('RequestDelete');
								break;

							default:
								break;
							}
						});
						
						
					}
				}
				dvContextMenu._doOnContextBeforeCall(e,{id:id});
				return false;
			});
			
			myDataView.attachEvent("onXLS",function(id,e){
				BodyLayOut.cells("c").progressOn();
			});
			
			myDataView.attachEvent("onXLE",function(id,e){
				BodyLayOut.cells("c").progressOff();
			});
			
			myDataView.config.pager.define("template", "{common.prev()} {common.pages()} {common.next()}");
			
			
		} else {
			myDataView.clearAll();
			buttonCheck = 1;

			BodyLayOut.cells("c").detachObject();
			BodyLayOut.cells("d").detachObject();
			BodyLayOut.cells("c").attachObject('gridbox');
			BodyLayOut.cells("d").attachObject('page');
		}
		
		if(SearchCnt > 0){
			
			doSearch();
		}

	}
	
	function rev_max_checked(){
		var rev_max = document.getElementById("rev_max");
		if(rev_max.checked){
			$('select_searchFormDTO_revisionA').checked = false;
			$('select_searchFormDTO_revisionM').checked = true;
		}else{
			$('select_searchFormDTO_revisionA').checked = true;
			$('select_searchFormDTO_revisionM').checked = false;
		}
		doSearch();
	}
	
	function tree_sublevel_checked(){
		var tree_sublevel = document.getElementById("tree_sublevel");
		if(tree_sublevel.checked){
			$('select_searchFormDTO_documentDTO_tree_sublevel').value = 'sub';
		}else{
			$('select_searchFormDTO_documentDTO_tree_sublevel').value = 'cur';
		}
		doSearch();
	}
	
	function dndAllRevChk(){
		var all_rev_chk = document.getElementById("allRev");
		if(all_rev_chk.checked){
			$('upTreeAndKind_docInforFormDTO_documentDTO_chk_all_rev').value = 'ck';
		}else{
			$('upTreeAndKind_docInforFormDTO_documentDTO_chk_all_rev').value = 'cn';
		}
		
	}
	
	function chngTreeAllRevChk(){
		var all_rev_chk = document.getElementById("chngTreeAllRev");
		if(all_rev_chk.checked){
			$('upTreeAndKind_docInforFormDTO_documentDTO_chk_all_rev').value = 'ck';
		}else{
			$('upTreeAndKind_docInforFormDTO_documentDTO_chk_all_rev').value = 'cn';
		}
		
	}
	
	function attrChngAllRevChk(){
		var all_rev_chk = document.getElementById("attrAllRev");
		if(all_rev_chk.checked){
			$('attrAll').value = 'ck';
		}else{
			$('attrAll').value = 'cn';
		}
		
	}
	function changeGrade(val){
//		alert(val);
		document.getElementById('change_text').value = "";
		document.getElementById('attrchange_date').value = "";
		document.getElementById('grade_id').value = "";
		document.getElementById('publicDocYN').value = "";
		document.getElementById('BOOK_CATEGORY').value = "";
		j$("#change_text").attr("disabled",false);
		j$("#attrBtnDiv").show();
		
		if(val == 'grade_id'){
			document.getElementById('inText').style.display = "none";
			document.getElementById('inDateText').style.display = "none";
			document.getElementById('grade').style.display = "block";
			document.getElementById('publicDoc').style.display = "none";
			document.getElementById('categoryMap').style.display = "none";
		}
		else if (val == 'doc_no' || val == 'rev_no'){
			var checkIds = mygrid.getSelectedRowId();
			checkIds = $w(checkIds.gsub(',', ' '));
			if(val == 'rev_no'){
				alert("REV_NO 수정은 파일 1개만 수정가능 합니다.");
				j$("#attrAllRev").prop("checked",false);
				$('attrAll').value = 'cn';
			}
			if(checkIds.size() != 1){
				document.getElementById('inText').style.display = "block";
				j$("#change_text").val("자료 1개만 선택바랍니다.");
				j$("#change_text").attr("disabled",true);
				j$("#attrBtnDiv").hide();
				document.getElementById('inDateText').style.display = "none";
				document.getElementById('grade').style.display = "none";
				document.getElementById('publicDoc').style.display = "none";
				document.getElementById('categoryMap').style.display = "none";	
			}
		}
		/* else if (val == 'update_date'){
			document.getElementById('inText').style.display = "none";
			document.getElementById('inDateText').style.display = "block";
			document.getElementById('grade').style.display = "none";
			document.getElementById('publicDoc').style.display = "none";
			document.getElementById('categoryMap').style.display = "none";
		} */
		else if (val == 'tree_id' || val == 'dockind_id'){
			document.getElementById('inText').style.display = "block";
			j$("#change_text").val("[자료분류변경]을 통해 변경가능합니다.");
			j$("#change_text").attr("disabled",true);
			j$("#attrBtnDiv").hide();
			document.getElementById('inDateText').style.display = "none";
			document.getElementById('grade').style.display = "none";
			document.getElementById('publicDoc').style.display = "none";
			document.getElementById('categoryMap').style.display = "none";
		}
		else if (val == 'ext' || val == 'f_size' || val == 'doc_type' || val == 'update_date'){
			document.getElementById('inText').style.display = "block";
			j$("#change_text").val("변경불가한 속성입니다.");
			j$("#change_text").attr("disabled",true);
			j$("#attrBtnDiv").hide();
			document.getElementById('inDateText').style.display = "none";
			document.getElementById('grade').style.display = "none";
			document.getElementById('publicDoc').style.display = "none";
			document.getElementById('categoryMap').style.display = "none";
		}
		else if (val == 'publicDoc'){
			document.getElementById('inText').style.display = "none";
			document.getElementById('inDateText').style.display = "none";
			document.getElementById('grade').style.display = "none";
			document.getElementById('publicDoc').style.display = "block";
			document.getElementById('categoryMap').style.display = "none";
		}
		else if (val == 'book_category'){
			document.getElementById('inText').style.display = "none";
			document.getElementById('inDateText').style.display = "none";
			document.getElementById('grade').style.display = "none";
			document.getElementById('publicDoc').style.display = "none";
			document.getElementById('categoryMap').style.display = "block";
		}
		else {
			document.getElementById('inText').style.display = "block";
			document.getElementById('inDateText').style.display = "none";
			document.getElementById('grade').style.display = "none";
			document.getElementById('publicDoc').style.display = "none";
			document.getElementById('categoryMap').style.display = "none";
		}
	}
	
	
</script>

</head>
<body style="height: 99.9%">
	<s:hidden name="equipmentSearchFormDTO.c_category" id="c_category"
			labelposition="left" labelSeparator="" value=""/>
			
 	<div id="paging_here"  class='pagination' style="width:100%;height:100%;display: none ">
 	</div>
 	<!-- 멀티다운로드 창 -->
	<div id="downloadRequestDiv" style="display: none;">
		<div id="ReqDownGridbox" style="position:relative;left:3px;width:398px;height:200px;margin-top:10px;"></div>
		<s:form id="inReqDownloadDoc" cssClass="wwFormTable AppForm" onsubmit="return false;">
			<s:hidden name="workFlowFormDTO.doc_seq" labelposition="left"
				cssStyle="width: 270px" label="doc_seq" labelSeparator=""
				readonly="true" />
			<s:hidden name="workFlowFormDTO.workflowDTO.wf_kind"
				labelposition="left" cssStyle="width: 250px" label="wf_kind"
				labelSeparator="" value="RD" readonly="true" />
			<s:textarea name="workFlowFormDTO.workflowAppDTO.remark" labelposition="left"
				cssStyle="width: 265px;height:80px" label="* %{getText('W_REQ_COMMTS')}"
				labelSeparator="" onkeyup="chkMsgLength(80,this,txt_Byte);" />
		</s:form>
		<span id ="txt_Byte" style="padding-left: 60; display:none;">0</span><!-- byte(최대 80byte 까지만 작성 가능합니다.) -->
		<div class="btnArea">
			<input type="button" class="btn" style="width: 100px" onclick="$('inReqDownloadDoc').AddTr('Down');" 
			       value="<t:t code="W_APPLINE_ADD"/>" /> 
			<input type="button" class="btn" style="width: 100px" onclick="reqDown_removeTr()" 
			       value="<t:t code="W_APPLINE_DEL"/>" />
			<input type="button" class="btn" style="width: 100px" onclick="reqDownload()" 
			        value="<t:t code="W_REQ"/>" /> 
		</div>
	</div>

 	<!-- 삭제요청 창 -->
 	<style type="text/css">
 		#inReqDeleteDoc .wwFormTable.AppForm{width:455px;margin:0;}
 		#inReqDeleteDoc .wwFormTable.AppForm td{border-top:0 none}
 	</style>
	<div id="ReqDeleteDiv" style="display: none;">
		<div id="ReqDeleteGridbox" style="width:453px; height:200px;margin-top:10px;"></div>
		<s:form id="inReqDeleteDoc" cssClass="wwFormTable AppForm" onsubmit="return false;">
			<s:hidden name="workFlowFormDTO.doc_seq" labelposition="left"
				cssStyle="width: 270px" label="doc_seq" labelSeparator=""
				readonly="true" />
			<s:hidden name="workFlowFormDTO.workflowDTO.wf_kind"
				labelposition="left" cssStyle="width: 250px" label="wf_kind"
				labelSeparator="" value="RDEL" readonly="true" />
			<s:textarea name="workFlowFormDTO.workflowAppDTO.remark" labelposition="left"
				cssStyle="width:100%;height:80px" label="%{getText('W_REQ_DEL_COMMTS')}"
				labelSeparator="" onkeyup="chkMsgLength(80,this,txt_Byte);" />
		</s:form>
		<span id ="txt_Byte" style="padding-left: 60; display:none;">0</span><!-- byte(최대 80byte 까지만 작성 가능합니다.) -->
		<div class="btnArea">
			<input type="button" class="btn" style="width: 100px"
					onclick="$('inReqDeleteDoc').AddTr('Delete');" value="<t:t code="W_APPLINE_ADD"/>" /> 
					<input type="button" class="btn" style="width: 100px"
					onclick="$('inReqDeleteDoc').RemoveTr()" value="<t:t code="W_APPLINE_DEL"/>" />
					<input type="button" class="btn" style="width: 100px"
					onclick="reqDelete()" value="<t:t code="W_REQ"/>" /> 
			<%-- <table align="center">
			<tr>
				<td>
					<input type="button" class="btn" style="width: 100px"
					onclick="$('inReqDeleteDoc').AddTr('Delete');" value="<t:t code="W_APPLINE_ADD"/>" /> 
					<input type="button" class="btn" style="width: 100px"
					onclick="$('inReqDeleteDoc').RemoveTr()" value="<t:t code="W_APPLINE_DEL"/>" />
					<input type="button" class="btn" style="width: 100px"
					onclick="reqDelete()" value="<t:t code="W_REQ"/>" /> 
				</td>
			</tr>
			</table> --%>
 			
		</div>
	</div>
		
<div id="SharedListDiv" style="display: none;">
		<div id="sharedgridbox" style="width: 100%; height: 300px;margin-top:10px;" style="float:left;"></div>
		<s:form id = "sharedForm" cssClass="wwFormTable AppForm" onsubmit="return false;">
						
			<tr>
				<td class="tdLabel">${t:s("W_SHARED_FOLDER","공유폴더")}</td>
				<td>
					<s:select id="shared_id" name="ShareFolderDTO.share_folder_name" theme="simple" list="shared_List" 
						cssStyle="width: 100%"/>
				</td>
			</tr>
			

		</s:form>
		<div class="btnArea">
			<table align="center">
			<tr>
				<td>
					<input type="button" class="btn" style="cursor:pointer" onclick="sharedAdd()" value="추가" />
					<!-- <input type="button" class="btn" style="cursor:pointer" onclick="sharedListDel()" value="담기목록삭제" /> -->
				</td>
			</tr>
			</table>

		</div>
	</div>
<div id="checkOutDiv" style="display: none;">
	<div id="gridbox10" style="width:625px;height:200px;margin-top:10px;"></div> 
		<div style="width:640px;margin-top:-9px;">
		<s:form action="/edms/dc/inCheckOutDoc">
			<s:hidden name="checkOutDTO.doc_seq" labelposition="left"
				label="doc_seq" labelSeparator=""
				readonly="true" />
				
			<s:hidden name="checkOutDTO.rev_no" labelposition="left"
				label="doc_seq" labelSeparator="" readonly="true" />
				
				<s:hidden name="checkOutDTO.ext" labelposition="left"
				label="doc_seq" labelSeparator="" readonly="true" />
				
			<s:hidden name="checkOutDTO.company_id_hidden" labelposition="left"
				label="doc_seq" labelSeparator="" readonly="true" />
			
			<table class="wwFormTable" width="100%"; border="0px";">	
			
			<tr style="display:none;">
				<td class="tdLabel">${t:s("W_DTKIND","설계구분")}</td>
				<td colspan="3">
					<s:radio list="#{'A':'자체설계','P':'외주설계'}"
							name="checkOutDTO.division_number" onclick="RCheck($('inCheckOutDoc_checkOutDTO_division_numberP').checked)"
							theme="simple" value="%{'A'}"/>

				</td>
			</tr>
			<tr>
				<td class="tdLabel">* ${t:s("W_ENTERING_CHK_DATE","체크인예정일")}</td>
				<td colspan="3">
					<s:hidden name="checkOutDTO.in_date"/>
					<s:textfield id="checkin_date"  cssStyle="width:90%;" theme="simple" onkeyup="this.value = date_mask(this.value).replace(/[^0-9\-]/g,'');" maxlength='10'/>
					<span><img id="cal_checkin_date" border="0" src="<s:url value='/img/calendar.png'/>"></span>
					<%-- <s:textfield name="checkOutDTO.in_date" labelposition="left" cssClass="Calendar" cssStyle="width: 200px;"
							label="%{getText('W_CREATE_DATE')}" labelSeparator=""  theme="simple" value="%{default_date}"/> --%>
				</td>
			</tr>
			<tr>
				<td class="tdLabel">* ${t:s("SAVE_PATH","저장경로")}</td>
				<td colspan="3">
					<s:textfield name="checkOutDTO.checkout_path" labelposition="left" cssStyle="width:90%;" label="%{getText('SAVE_PATH')}"
						labelSeparator="" onclick="Openfile.fsSelectFolder(this,'D')" value="" placeholder="이 곳을 클릭하여 경로를 선택해주세요." theme="simple" readonly="true" />
					<img width="22px" src="/img/ico/folderOpen.gif" onclick="Openfile.fsSelectFolder(this,'D')">
				</td>
			</tr>
			<tr>
				<td class="tdLabel">${t:s("W_REMARK_Reason","사유")}</td>
				<td colspan="3">
					<s:textarea name="checkOutDTO.commts" labelposition="left"
						cssStyle="width: 92%;height:100px;" label="* %{getText('W_REMARK_Reason')}"
						labelSeparator="" theme="simple" />
				</td>
			</tr>
			</table>
		</s:form>
		</div>

		<div class="btnArea" style="text-align: center">
				<input type="button" class="btn" style="cursor:pointer" onclick="CheckOut()" value="check out" />
		</div>
	</div>



	<!-- 출력 요청창 -->
	<div id="ReqPrintDiv" style="display: none;">
		<div id="ReqPrintGridDiv" style="position:relative;left:1px;width:400px; height:200px;margin-top:10px;"></div>
		<s:form id="inReqPrint" cssClass="wwFormTable AppForm" onsubmit="return false;">
			<s:hidden name="workFlowFormDTO.workflowDTO.wf_kind"
				labelposition="left" cssStyle="width: 270px" label="doc_seq"
				labelSeparator="" value="RP" readonly="true" />
			<s:hidden name="workFlowFormDTO.doc_seq" labelposition="left"
				cssStyle="width: 300px" label="doc_seq" labelSeparator=""
				readonly="true" />
			<s:select name="workFlowFormDTO.workflowDetailDTO.req_size"
				labelposition="left" cssStyle="width: 260px;"
				label="* %{getText('W_SIZE')}" labelSeparator="" list="sizelist"
				headerValue="" headerKey="" />
			<s:textfield name="workFlowFormDTO.workflowDetailDTO.req_count"
				labelposition="left" label="* %{getText('W_PAPER_CNT')}"
				cssStyle="width: 250px;" labelSeparator="" />
			<s:textfield name="workFlowFormDTO.workflowDTO.emp_name"
				label="* %{getText('W_PRINT_WORK')}" cssClass="empSearchAll"
				labelposition="left" labelSeparator="" cssStyle="width: 255px" />
			
			<s:textfield name="workFlowFormDTO.workflowDetailDTO.req_tel"
				labelposition="left" label="%{getText('W_PHONE')}"
				cssStyle="width: 250px" labelSeparator="" />
			<s:textarea name="workFlowFormDTO.workflowAppDTO.remark"
				labelposition="left" cssStyle="width: 260px"
				label="%{getText('W_REMARK_Reason')}" labelSeparator="" />

		</s:form>
		
		<div class="btnArea">	
			<input type="button" class="btn" style="width: 100px"
				onclick="$('inReqPrint').AddTr('Print');" value="<t:t code="W_APPLINE_ADD"/>" /> 
				<input type="button" class="btn" style="width: 100px"
				onclick="$('inReqPrint').RemoveTr()" value="<t:t code="W_APPLINE_DEL"/>" />
				<input type="button" class="btn" style="width: 100px"
				onclick="reqPrint()" value="<t:t code="W_REQ"/>" /> 
			<%-- <table align="center">
			<tr>
				<td>
				<input type="button" class="btn" style="width: 100px"
				onclick="$('inReqPrint').AddTr('Print');" value="<t:t code="W_APPLINE_ADD"/>" /> 
				<input type="button" class="btn" style="width: 100px"
				onclick="$('inReqPrint').RemoveTr()" value="<t:t code="W_APPLINE_DEL"/>" />
				<input type="button" class="btn" style="width: 100px"
				onclick="reqPrint()" value="<t:t code="W_REQ"/>" /> 
				</td>
			</tr>
			</table> --%>
			
		</div>
	</div>

	
	<!-- 변경 요청창 -->
	<div id="ReqChangeDiv" style="display: none;">
		<div id="ReqChangegridbox" style="width:729px; height: 300px;margin-top:10px;" style="float:left;"></div>
		<s:form id = "Request" cssClass="wwFormTable AppForm" onsubmit="return false;" width="100%">
			<s:hidden name="workFlowFormDTO.doc_seq" labelposition="left"
				cssStyle="width: 270px" label="doc_seq" labelSeparator=""
				readonly="true" />
			<s:hidden name="workFlowFormDTO.workflowDTO.wf_kind"
				labelposition="left" cssStyle="width: 270px" label="doc_seq"
				labelSeparator="" value="RC" readonly="true" />

			<s:hidden name="workFlowFormDTO.workflowDetailDTO.req_tel"
				labelposition="left" label="%{getText('W_PHONE')}"
				cssStyle="width: 270px" labelSeparator="" />
			<s:hidden name="workFlowFormDTO.workflowAppDTO.markup_list"
				labelposition="left" cssStyle="width: 270px" label="Mark Up"
				labelSeparator="" />
			<s:hidden name="workFlowFormDTO.workflowDTO.emp_id"
				labelpostion="left" cssStyle="width: 270px; height:100px"
				label="%{getText('W_CHANGE_WORK')}" labelSeparator="" />
			<tr style="display:none">
				<td class="tdLabel">${t:s("W_ATTACHMENT","첨부파일")}1234</td>
					<td colspan="3">
					<s:textfield name="workFlowFormDTO.workflowDetailDTO.attach_file" 
							readonly="true" theme="simple" style="width: 450px;" />
					</td>
				
			</tr>
			<tr>
				<td class="tdLabel">* ${t:s("W_CHANGE_WORK","작업담당자")}</td>
				<td>
					<s:textfield name="workFlowFormDTO.workflowDTO.emp_name" labelposition="left" theme="simple" 
					   labelSeparator="" cssStyle="width:520px" /> 
					<input type="button" class="button_dot" value="..." style="width:30px; text-align:center;" class="btn"
					onclick="SearchEmpPopupAll(this,'Request_workFlowFormDTO_workflowDTO_emp_id','Request_workFlowFormDTO_workflowDTO_emp_name')" />
				</td>
			</tr>	
			<s:textarea name="workFlowFormDTO.workflowAppDTO.remark" labelposition="left" cssStyle="width: 530px;height:70px;"
						label="%{getText('W_REMARK_Reason')}" labelSeparator="" />

		</s:form>
		<div class="btnArea" style="text-align: center">
					<input type="button" class="btn" style="width: 100px" onclick="$('Request').AddTr('Change');"
						value="<t:t code="W_APPLINE_ADD"/>" /> 
					<input type="button" class="btn" style="width: 100px" onclick="$('Request').RemoveTr()"
						value="<t:t code="W_APPLINE_DEL"/>" />
					<!-- <input type="button" class="btn" style="width: 100px" onclick="markup()" value="Mark Up" />  -->
<!-- 					<input type="button" class="btn" style="width: 100px" onclick="doReqChangeDivAttachList()" value="파일첨부" />  -->
					<input type="button"class="btn" style="width: 100px" onclick="reqChange()"
						value="<t:t code="W_REQ"/>" /> 
		</div>
	</div>


	<div id="ChangeTreeDiv" style="display: none;">
		<s:form action="/edms/dc/upTreeAndKind">
			<s:hidden name="docInforFormDTO.documentDTO.doc_seq"
				labelposition="left" label="%{getText('W_TREE_ID')}"
				labelSeparator="" readonly="true" cssStyle="width: 200px" />
			<s:hidden name="docInforFormDTO.documentDTO.doc_no"
				labelposition="left" label="%{getText('W_DOC_NO')}"
				labelSeparator="" readonly="true" cssStyle="width: 200px" />
			<s:hidden name="docInforFormDTO.documentDTO.chk_all_rev"
				labelposition="left" label="%{getText('W_TREE_ID')}"
				labelSeparator="" readonly="true" cssStyle="width: 200px" />
			<s:hidden name="docInforFormDTO.documentDTO.doc_seq_z"
				labelposition="left" label="%{getText('W_TREE_ID')}"
				labelSeparator="" readonly="true" cssStyle="width: 200px" />
			<s:hidden name="docInforFormDTO.documentDTO.doc_seq_list"
				labelposition="left" label="%{getText('W_TREE_ID')}"
				labelSeparator="" readonly="true" cssStyle="width: 200px" />
				
			<tr>
				<td class="tdLabel">${t:s("W_DATA_TREE","자료분류")}</td>
				<td><s:hidden name="docInforFormDTO.documentDTO.tree_id" /> <s:textfield
						name="docInforFormDTO.documentDTO.tree_name" labelposition="left"
						theme="simple" labelSeparator="" cssStyle="width: 83%" /> <input
					type="button" value="..." class="button_dot" 
					onclick="TreeHierarchy(this,'upTreeAndKind_docInforFormDTO_documentDTO_tree_id','upTreeAndKind_docInforFormDTO_documentDTO_tree_name')" /></td>
					<td style="width: 15px;"><input type="button" onclick="doChange('tree')" class='btn' value="<t:t code="W_APPLY"/>"/>
					</td>
			
			</tr>

			<tr>
				<td class="tdLabel">${t:s("W_DOCKIND_NAME","Area")}</td>
				<td><s:hidden name="docInforFormDTO.documentDTO.dockind_id" />
					<s:textfield name="docInforFormDTO.documentDTO.dockind_name"
						labelposition="left" theme="simple" labelSeparator=""
						cssStyle="width: 83%" /> <input type="button" value="..." class="button_dot" 
					onclick="KindTreeHierarchy(this,'upTreeAndKind_docInforFormDTO_documentDTO_dockind_id','upTreeAndKind_docInforFormDTO_documentDTO_dockind_name')" /></td>
					<td style="width: 15px;"><input type="button" onclick="doChange('kind')" class='btn' value="<t:t code="W_APPLY"/>"/>
					</td>
			</tr>
			
		</s:form>
		<div class="btnArea">
			<input type="button" onclick="doChangeTreeClose()" class='btn' value="닫기"/>
		</div>
		<div class="btnArea">
			<input type="checkbox" id="chngTreeAllRev" onchange="chngTreeAllRevChk();"  checked="checked">동일한 자료번호(모든 Rev_no)의 속성을 일괄 변경합니다.	
		</div>
	</div>

	
	
	<div id="treeBox"
		style="width: 100%; height: 100%; background-color: #ffffff; overflow: auto; display: none"></div>
	<div id="gridbox" style="width: 98%; height: 95%;"></div>
	<div id="page"  class="pagination">
		<span id="pagingArea"></span>&nbsp;<span id="infoArea"></span>
	</div> 

	<div id="kindtreeBox"
		style="width: 100%; height: 100%; background-color: #ffffff;overflow: auto;">
	</div>
 

 	<!-- <div id="favoriteTreeBox"
		style="width: 100%; height: 100%; background-color: #ffffff; overflow: auto;">
	</div>
	
	<div id="sharedTreeBox"
		style="width: 100%; height: 100%; background-color: #ffffff;  overflow: auto;">
	</div> -->
	
	
	
	<div id="Search_desc" class="searchBox list" style="display: none">
	</div>
	
	<div id="rec_search_list" class="historyBox" style="display: none;">
	    <article style="margin-bottom: 20px;">
	        <p style="float: left;">
	            <b>최근 검색어</b>
	        </p>
	        <p style="float: right;">
	            <a onclick="javascript:deleteSearchWord('','Search');" style="text-align: right;"><b>전체삭제</b></a>
	        </p>
	    </article>
	
	    <hr/>
	    <ul id="dropdown"></ul>
	    <hr/>
	    
	     <article style="margin-bottom: 5px;">
	        <p style="float: left;">
	            <a onclick="javascript:j$('#rec_search_list').css('display','none');"><b>취소</b></a>
	        </p>
	        <p style="float: right;">
	            <a id="autoSaveOn" onclick="javascript:inAutoSave('SA','true');" style="text-align: right; display:none;"><b>자동저장 켜기</b></a>
	            <a id="autoSaveOff" onclick="javascript:inAutoSave('SA','false');" style="text-align: right; display:none;"><b>자동저장 끄기</b></a>
	        </p>
	    </article>
	</div>
	
	<div id="favorite_list" class="favoriteBox" style="display: none;">
	    <article style="margin-bottom: 20px;">
	        <p style="float: right;">
	            <a onclick="javascript:deleteSearchWord('','Favorite');" style="text-align: right;"><b>전체삭제</b></a>
	        </p>
	    </article>
	    <hr/>
	    <ul id="FavoriteDropdown"></ul>
	</div>
	
			
	<div id="docInfoDiv"
		style="width: 100%; height: 100%; background-color: #ffffff; overflow: auto; overflow-x: hidden; overflow-y: hidden; display: none; text-align:center;z-index: 9999">
	</div>
	
 	<div id="itemFileUploadDiv" style="display: none;">
		<s:form target="if_fileuploader" name="itemFmFileUpload" id="itemFmFileUpload" method='post' enctype='multipart/form-data' action='/edms/dc/inCADTextExcel.action'>
			<input id="upFile" name="itemfile" type="file" style="width: 350px;">
			<iframe name="if_fileuploader" style="display: none;"></iframe>
		</s:form>	 	
    </div>
		
	<div id="fileUploadDiv" style="display: none;">
		<s:form target="ifmfileSender10" name="fmTempuserFileUpload" id="fmTempuserFileUpload" method='post' enctype='multipart/form-data' action='./inTempItemData.action'>
			<input id="fileSelecter2" name="file" type="file" onchange="javascript:if(checkExt2()){waiter.show();submit();};" style="width: 350px;">
		</s:form>
		<iframe name='ifmfileSender10' id='ifmfileSender10' style="display: none;"></iframe>
	</div>
	
 	<div id="tagInfoDiv" style="display: none; text-align:center;">
		<div id="tagInfogridbox" style="width:100%; height:500px;"></div>
	</div>

 
 
  	<!-- add20170215 자료조회 검색추가 -->
 	<div id="searchDiv" style="width: 100%; height: 100%; background-color: #ffffff;overflow: auto; display: none;">		
		<div id ="searchinfo" style="width: 97%; padding-top: 0px; margin-left: 10px;">
		<s:form action="/edms/dc/select" id="select">
			
			<table class="wwFormTable4"  width=100% >
			
			<s:hidden name="searchFormDTO.documentDTO.tree_sublevel" 
				value="sub"
				labelposition="left" label="%{getText('W_TREE_ID')}"
				labelSeparator="" readonly="true" cssStyle="width: 250px"/>
			<s:hidden name="searchFormDTO.documentDTO.tree_id"
				labelposition="left" label="%{getText('W_TREE_ID')}"
				labelSeparator="" readonly="true" cssStyle="width: 250px" />
			<s:hidden name="searchFormDTO.documentDTO.dockind_id"
				labelposition="left" label="%{getText('W_DATA_KIND')}"
				labelSeparator="" cssStyle="width: 250px" />
			<s:hidden name="searchFormDTO.autoSaveYN"/>
			<s:hidden name="searchFormDTO.statistics_sort"/>
			<s:hidden name="searchFormDTO.andor"/>
			<s:hidden name="searchFormDTO.documentDTO.except_tree"/>
			
			<s:hidden name="searchFormDTO.startDate"/>
			<s:hidden name="searchFormDTO.endDate"/>
			
				<tr>
					<td class="tdLabel">${t:s("W_SHAPE","종류")}</td>
					<td style="width: 400px;">
						<%-- <s:radio name="searchFormDTO.documentDTO.doc_type"
						list="typecodelist" theme="simple" />	 --%>
						<s:textfield name="searchFormDTO.documentDTO.doc_type"
						theme="simple" cssStyle="width: 250px"/>
					</td>
				</tr>
				<tr style="display: none;">
					<td class="tdLabel">${t:s("W_TREE_NAME","분류명")}</td>
					<td>
						<s:textfield name="searchFormDTO.treeDTO.tname" readonly="true"
						theme="simple" cssStyle="width: 200px" />
					</td>
				</tr>
				<tr style="display: none;">
					<td class="tdLabel">${t:s("W_DATA_KIND","종류명")}</td>
					<td>
						<s:textfield name="searchFormDTO.documentDTO.dockind_name" readonly="true"
						theme="simple" cssStyle="width: 200px" />
					</td>
				</tr>
				<tr>
				<td class="tdLabel">${t:s("W_DATA_NO","자료번호")}</td>
					<td>
						<s:textfield name="searchFormDTO.documentDTO.doc_no"
						theme="simple" cssStyle="width: 250px" onKeyPress="javascript:if(event.keyCode==13){ doSearch(); }"/>
					</td>
				</tr>
				<tr>
				<td class="tdLabel">${t:s("W_DATA_NAME","자료명")}</td>
					<td>
						<s:textfield name="searchFormDTO.documentDTO.doc_name"
						theme="simple" cssStyle="width: 250px" onKeyPress="javascript:if(event.keyCode==13){ doSearch(); }"/>
					</td>
				</tr>
				<tr style="display:none">
					<td class="tdLabel">${t:s("W_ITEM_NAME","아이템명")}</td>
					<td>
						<s:textfield name="searchFormDTO.item" labelposition="left"
						theme="simple" cssStyle="width: 250px" onKeyPress="javascript:if(event.keyCode==13){ doSearch(); }"/>
					</td>
				</tr>
				<tr style="display: none;">
					<td class="tdLabel">${t:s("W_EXTERNAL_DOC_NO","외부자료번호")}</td>
					<td>
						<s:textfield name="searchFormDTO.documentDTO.com_doc_no"
						theme="simple" cssStyle="width: 200px" />
					</td>
				</tr>
				<tr style="display: none;">
					<td class="tdLabel">${t:s("W_PROJECT_NAME","프로젝트명")}</td>
					<td>
						<s:textfield name="searchFormDTO.documentDTO.project_name"
						theme="simple" cssStyle="width: 200px" onKeyPress="javascript:if(event.keyCode==13){ doSearch(); }"/>
					</td>
				</tr>
				<tr style="display: none;">
					<td class="tdLabel">${t:s("W_DATA_GRADE","자료등급")}</td>
					<td>
						<s:select name="searchFormDTO.documentDTO.grade_id"
						theme="simple" list="gradecodelist" cssStyle="width: 200px" />
					</td>
				</tr>
				<tr style="display: none;">
					<td class="tdLabel">${t:s("W_MAINTAIN","보존년한")}</td>
					<td>
						<s:select name="searchFormDTO.documentDTO.maintain_id"
						theme="simple" list="maintcodelist" cssStyle="width: 200px" />
					</td>
				</tr>
				<tr style="display: none;">
					<td class="tdLabel">비고</td>
					<td>
						<s:textfield name="searchFormDTO.documentDTO.commts"
						theme="simple" cssStyle="width: 200px" />
					</td>
				</tr>
				<tr style="display: none;">
					<td class="tdLabel">${t:s("W_SEARCHLIST","검색")}</td>
					<td>
						<s:textfield name="searchFormDTO.searchString"
						theme="simple" cssStyle="width: 200px" /> 
					</td>
				</tr>
				<tr style="display: none;">
					<td class="tdLabel">${t:s("W_CURRENT_PAGE_URI","현재페이지")}</td>
					<td>
						<s:textfield name="searchFormDTO.page_uri"
						theme="simple" cssStyle="width: 200px" /> 
					</td>
				</tr>
				<tr style="display: none;">
					<td class="tdLabel">${t:s("W_EXT","확장자")}</td>
					<td>
						<%-- <s:select name="searchFormDTO.documentDTO.ext" 
						list="extlist" theme="simple" cssStyle="width: 200px" /> --%>
						<s:textfield 
						cssClass="empSearch" name="searchFormDTO.documentDTO.ext"
						theme="simple" cssStyle="width: 200px" /> 
					</td>
				</tr>
				<tr style="display: none;">
					<td class="tdLabel">${t:s("W_UPDATE_EMP","수정자")}</td>
					<td>
						<s:textfield 
						cssClass="empSearch" name="searchFormDTO.documentDTO.u_user_name"
						theme="simple" cssStyle="width: 200px" /> 
					</td>
				</tr>
				<tr style="display: none;">
					<td class="tdLabel">${t:s("W_FIRST_INSERT_EMP","최초등록자")}</td>
					<td>
						<s:textfield cssClass="empSearch" name="searchFormDTO.documentDTO.i_user_name"
						theme="simple" cssStyle="width: 200px" /> 
					</td>
				</tr>	
				<tr style="display: none;">
					<td class="tdLabel">${t:s("W_FIRST_INSERT_EMP","최초등록자")}</td>
					<td>
						<s:textfield cssClass="empSearch" name="searchFormDTO.documentDTO.drawing_no"
						theme="simple" cssStyle="width: 200px" /> 
					</td>
				</tr>	
				<tr>
					<td class="tdLabel">${t:s("W_REV","Rev")}</td>
					<td>
						<s:radio list="#{'A':'All','M':'Max','N':'Min'}"
							name="searchFormDTO.revision" 
							theme="simple" value="%{'A'}">
						</s:radio>
					</td>
				</tr>				
							<tr class="tdLabel">
					<td class="tdLabel">${t:s("W_LOCK_STATUS","잠금상태")}</td>
					<td>
						<s:radio list="#{'A':'All','T':'Lock','F':'UnLock'}"
							name="searchFormDTO.documentDTO.lock_status" 
							theme="simple" value="%{'A'}">
						</s:radio>
					</td>
				</tr>
				
			</table>
			<table align="center">
				<tr>				
					<td><br>
 						<input type="button" class="btn2" onclick="doSearch()" value="조회" style="cursor:pointer"/> 
					</td>
				</tr>
			</table>

		</s:form>
		</div>
	</div>
	<!-- end -->
	
	
	<!-- 200204 외주배포 팝업창 Start-->
	<div id="OutPublishDiv" style="display: none;">
		<div id="OPgridbox" style="width: 100%; height: 300px;margin-top:10px;" style="float:left;"></div>
		<s:form id = "OutPublish" action="/edms/wf/OutPublish" cssClass="wfFormTable AppForm">
			<s:hidden name="workFlowFormDTO.workflowDTO.wf_kind"
				labelposition="left" cssStyle="width: 270px" label="doc_seq"
				labelSeparator="" value="OD" readonly="true" />
				
			<s:hidden name="workFlowFormDTO.outSourcingDTO.doc_seq_list" labelposition="left"
				label="doc_seq" labelSeparator="" readonly="true" />
			<s:hidden name="workFlowFormDTO.outSourcingDTO.distributor_id" labelposition="left"
				label="doc_seq" labelSeparator="" readonly="true" />
				<s:hidden name="workFlowFormDTO.outSourcingDTO.emp_id" labelposition="left"
				label="doc_seq" labelSeparator="" readonly="true" />
				
			<s:hidden name="workFlowFormDTO.workflowAppDTO.remark"
				labelposition="left" cssStyle="width: 270px;height:100px;"
				label="%{getText('W_REMARK_Reason')}" labelSeparator="" onmousemove=" " />
			<tr>
				<td class="tdLabel">${t:s("W_OUTCOM_COMPANY","외주업체")}</td>
				<td>
					<s:select name="workFlowFormDTO.outSourcingDTO.company_code" theme="simple" list="Company_List" 
						cssStyle="width: 80%" onchange="CompanyChk()"/>
				</td>
			</tr>
			<tr>
				<td class="tdLabel">${t:s("W_OUTCOM_ADMIN","외주담당자")}</td>
				<td>
					<select id="Distributor_list" name="workFlowFormDTO.outSourcingDTO.out_emp_id" style="width: 80%;"> 
						<option value="">외주업체를 선택해주세요</option>	
					</select>
				</td>	
			</tr>
			<tr>
				<td class="tdLabel">${t:s("W_EXPIRED_DATE","조회만료기한")}</td>
				<td><s:hidden name="workFlowFormDTO.outSourcingDTO.expired_date"/>
				<s:textfield id="expired_date"  cssStyle="width:40%;" theme="simple" onkeyup="this.value = date_mask(this.value).replace(/[^0-9\-]/g,'');" maxlength='10'/>
				<span><img id="cal_expired_date" border="0" src="<s:url value='/img/calendar.png'/>"></span>
				<%-- <s:textfield name="workFlowFormDTO.outSourcingDTO.expired_date"
							labelposition="left" cssClass="Calendar" cssStyle="width: 40%;"
							label="%{getText('W_CREATE_DATE')}" labelSeparator=""
							readonly="true" theme="simple" value="%{default_date}"/> --%>
				</td>			
  		    </tr>
  		    <tr>
				<td class="tdLabel">${t:s("W_PRINT_CNT","출력회수")}</td>
				<td><s:textfield name="workFlowFormDTO.outSourcingDTO.print_max_cnt" theme="simple" value="10" /></td>
  		    </tr>
  		    <tr>
				<td class="tdLabel">${t:s("W_DOWN_CNT","다운로드횟수")}</td>
				<td><s:textfield name="workFlowFormDTO.outSourcingDTO.down_max_cnt" theme="simple" value="10"/></td>
  		    </tr>
  		    <tr>
				<td class="tdLabel">${t:s("W_REMARK_Desc","비 고")}</td>
				<td><s:textarea name="workFlowFormDTO.outSourcingDTO.comment"
						theme="simple" style="width: 300px; height: 100px;" /></td>
			</tr>			
			
		</s:form>

		<div id="OPButtonDiv" style="width: 100%; height: 300px;">
			<table align="center">
				<tr>				
					<td>
 						<input type="button" class="btn" style="width: 80px" onclick="$('OutPublish').AddTr('OD');" value="<t:t code="W_APPLINE_ADD"/>" style="cursor:pointer"/> 
 						<input type="button" class="btn" style="width: 80px" onclick="$('OutPublish').RemoveTr();" value="<t:t code="W_APPLINE_DEL"/>" style="cursor:pointer"/>
 						<!-- <input type="button" class="btn" style="width: 80px" onclick="doDocDelete()" value="자료삭제" style="cursor:pointer"/> -->
 						<input type="button" class="btn" style="width: 80px" onclick="OutPublish_Request()" value="배포요청" style="cursor:pointer"/>		
					</td>
				</tr>
			</table>
		</div>
	</div>	 
 	<!-- 200204 외주배포 팝업창 End-->
 	
 	<!-- 2020-02-12 외주변경요청 팝업창 -->
	<div id="OutReqChangeDiv" style="display: none;">
		<div id="OutReqChangegridbox" style="width: 729px; height: 300px;margin-top:10px;" style="float:left;"></div>
		<s:form id = "OutRequest" cssClass="wwFormTable AppForm" onsubmit="return false;">
			<s:hidden name="workFlowFormDTO.doc_seq" labelposition="left"
				cssStyle="width: 270px" label="doc_seq" labelSeparator=""
				readonly="true" />
			<s:hidden name="workFlowFormDTO.workflowDTO.wf_kind"
				labelposition="left" cssStyle="width: 270px" label="doc_seq"
				labelSeparator="" value="OCO" readonly="true" />
			<s:hidden name="workFlowFormDTO.workflowDetailDTO.req_tel"
				labelposition="left" label="%{getText('W_PHONE')}"
				cssStyle="width: 270px" labelSeparator="" />
			<s:hidden name="workFlowFormDTO.workflowAppDTO.mark_up"
				labelposition="left" cssStyle="width: 270px" label="Mark Up"
				labelSeparator="" />
			<s:hidden name="workFlowFormDTO.workflowAppDTO.markup_list"
				labelposition="left" cssStyle="width: 270px" label="Mark Up"
				labelSeparator="" />
			<s:hidden name="workFlowFormDTO.workflowAppDTO.attach_list"
				labelposition="left" cssStyle="width: 270px" label="attach"
				labelSeparator="" />
			<s:hidden name="workFlowFormDTO.workflowDTO.emp_id"
				labelpostion="left" cssStyle="width: 270px; height:100px"
				label="%{getText('W_CHANGE_WORK')}" labelSeparator="" />

			<tr style="display:none">
				<td class="tdLabel">${t:s("W_ATTACHMENT","첨부파일")}</td>
				<td>
					<s:textfield name="workFlowFormDTO.workflowDetailDTO.attach_file" 
						readonly="true" theme="simple" style="width: 450px;" />
				</td>		
			</tr>				
			<tr>
				<td class="tdLabel">${t:s("W_OUTCOM_COMPANY","외주업체")}</td>
				<td>
					<s:select name="workFlowFormDTO.outSourcingDTO.company_code" theme="simple" list="Company_List" 
						cssStyle="width: 80%" onchange="OutCompanyChk()"/>
				</td>
			</tr>
			<tr>
				<td class="tdLabel">${t:s("W_OUTCOM_ADMIN","외주담당자")}</td>
				<td>
					<select id="out_emp_list" name="workFlowFormDTO.outSourcingDTO.out_emp_id" style="width: 80%;"> 
						<option value="">외주업체를 선택해주세요</option>	
					</select>
				</td>	
			</tr>
			<tr>
				<td class="tdLabel">${t:s("W_EXPIRED_CHANGE_DATE","외주 수정요청 만료기한")}</td>
				<td><s:hidden name="workFlowFormDTO.outSourcingDTO.expired_change_date"/>
				<s:textfield id="expired_change_date"  cssStyle="width:40%;" theme="simple" onkeyup="this.value = date_mask(this.value).replace(/[^0-9\-]/g,'');" maxlength='10'/>
				<span><img id="cal_expired_change_date" border="0" src="<s:url value='/img/calendar.png'/>"></span>
				<%-- <s:textfield name="workFlowFormDTO.outSourcingDTO.expired_change_date"
							labelposition="left" cssClass="Calendar" cssStyle="width: 78%;"
							label="%{getText('W_CREATE_DATE')}" labelSeparator=""
							readonly="true" theme="simple" value="%{default_date}"/> --%>
				</td>			
  		    </tr>	
			<s:textarea name="workFlowFormDTO.workflowAppDTO.remark" labelposition="left" cssStyle="width: 80%;height:100px;"
						label="%{getText('W_REMARK_Reason')}" labelSeparator="" />

		</s:form>
		<div class="btnArea">
			<table align="center">
			<tr>
				<td>
					<input type="button" class="btn" style="width: 80px" onclick="$('OutRequest').AddTr('OCO');"
						value="<t:t code="W_APPLINE_ADD"/>" /> 
					<input type="button" class="btn" style="width: 80px" onclick="$('OutRequest').RemoveTr()"
						value="<t:t code="W_APPLINE_DEL"/>" />
					<!-- <input type="button" class="btn" style="width: 80px" onclick="OutMarkup()" value="Mark Up" /> 
					<input type="button" class="btn" style="width: 80px" onclick="doOutReqChangeDivAttachList()" value="파일첨부" /> --> 
					<input type="button" class="btn" style="width: 80px" onclick="OutReqChange()"
						value="<t:t code="W_REQ"/>" /> 
				</td>
			</tr>
			</table>

		</div>
	</div>
 	<!-- 2020-02-12 외주변경요청 팝업창 END-->
 	
 	<!-- 수정요청 파일첨부 -->
 	<div id="ReqChangeDivAttachList" style="display: none;">
	<div id="attachBox" style="width: 100%; height: 300px;margin-top:10px;"
			style="float:left;"></div>
	</div>	
	
	<!-- 외주수정요청 파일첨부 -->
 	<div id="outReqChangeDivAttachList" style="display: none;">
		<div id="outAttachBox" style="width: 100%; height: 300px;margin-top:10px;" style="float:left;"></div>
	</div>
	
	<!-- 속성변경 -->
	<div id="attrChangeDiv" align="center" style="display: none;">
	
		<table class="wwFormTable AppForm">
			<tr>
				<td class="tdLabel">
					<select id='attrchange' onchange="changeGrade(this.value);">
					</select>
				</td>
				<td id="inText">
					<input type="text" id="change_text" value="" style="width: 95%">
				</td>
				<td id="inDateText" style="display:none">
					<s:textfield id="attrchange_date" theme="simple" onkeyup="this.value = date_mask(this.value).replace(/[^0-9\-]/g,'');" maxlength='10'/>
					<span><img id="cal_attrchange_date" border="0" src="<s:url value='/img/calendar.png'/>"></span>
				</td>
				<td id="grade" style="display:none">
					<s:select id="grade_id"  theme="simple" list="gradecodelist" cssStyle="width: 200px" />
				</td>
				<td id="publicDoc" style="display:none">
					<s:select id="publicDocYN" cssStyle="width: 200px" theme="simple" list="#{'N':'N','Y':'Y'}"/>
				</td>
				<td id="categoryMap" style="display:none">
					<s:select id="BOOK_CATEGORY"  theme="simple" list="categoryMap" cssStyle="width: 200px" />
				</td>
			</tr>
			 
		</table>
		<div id="attrBtnDiv" class="btnArea">
			<input type="button" class="btn" style="width: 80px" onclick="attrChangeSave()" value="적용" /> 
			<input type="button" class="btn" style="width: 80px" onclick="attrChangeCancel()" value="닫기" />
		</div>
		<div class="btnArea">
			<input type="checkbox" id="attrAllRev" onchange="attrChngAllRevChk();" checked="checked">동일한 자료번호(모든 Rev_no)의 속성을 일괄 변경합니다.
			<input type="hidden" id="attrAll" value="ck"/>
		</div>
		
	</div>
	
	<!-- 드래그앤드랍 분류변경 -->
	<div id="DragTreeChangeDiv" align="center" style="display: none;">
	
		<div class="btnArea">
			<input type="button" class="btn" style="width: 80px" onclick="dndTreeChange()" value="적용" /> 
			<input type="button" class="btn" style="width: 80px" onclick="dndTreeCancel()" value="닫기" />
		</div>
		
		<div>
			<input type="checkbox" id="allRev" onchange="dndAllRevChk();"  checked="checked">동일한 자료번호(모든 Rev_no)의 속성을 일괄 변경합니다.	
		</div>
	</div>
	
	<!-- 드래그앤드랍 분류변경 -->
	<div id="createTreeDiv" align="center" style="display: none;">
		<input type="hidden" id="contextTreeId">
		<table class="wwFormTable AppForm">
			<tr >
				<td class="tdLabel">${t:s("W_TREE_NAME","분류명")}</td>
				<td>
					<s:textfield name="targetName" theme="simple" style="width: 150px;" />
				</td>		
			</tr>				
			<tr>
				<td class="tdLabel">분류코드</td>
				<td>
					<s:textfield name="targetCode" theme="simple" style="width: 150px;" />	
				</td>
			</tr>
		</table>
	
		<div align="center" style="text-align: center">
			<input type="button" class="btn" style="width: 80px" onclick="createTree()" value="만들기" /> 
		</div>
		
	</div>
	
	<!-- 200402 출력 팝업창 Start-->
	<div id="PrintPopUpDiv2" style="display: none;">
		<div id="PrintPopUpgridbox" style="width: 100%; height: 400px" style="float:left;"></div>
		<div id="PrintPopUpButtonDiv2" style="width: 100%; height: 100px; text-align: center; margin-top: 20px">
		<input type="button" class="btn" onclick="print()" value="출력" style="cursor:pointer"/> 	
		<%-- <table>
				<tr>				
					<td style="padding-top:4px;padding-right:10px">
						<!-- <input type="button" class="btn" style="width: 80px" onclick="ChangeList1('UP')" value="Up" style="cursor:pointer"/>	
 						<input type="button" class="btn" style="width: 80px" onclick="ChangeList1('DOWN')" value="Down" style="cursor:pointer"/> -->
 						
					</td>
				</tr>
			</table> --%>
		</div>
	</div>	 
 	<!-- 200402 출력 팝업창 End-->

	<div id="doc_list_template" style="display:none">
		<div class='doc_img'>
		    <div style="position: absolute;">
		        <div style="position: relative; top: 0px; left: 0px; width: 20px; height: 20px">
		        	<img id="lock_img" src="/img/ico/#lock_img#" style="width: 20px; height: 20px" onError="this.style.display='none'" 
		        	onLoad="this.style.display='block'" ondragstart='return false'></img>
	        	</div>
		    </div>
		   <!--   <img src='/edmsimages/is#img#.png' class='doc_img_org' onmouseover="javascript:dataviewImg=true;" onmouseout="javascript:dataviewImg=false;" 
	    		onError="this.style.visibility='hidden'" onload="this.style.visibility='visible'" ondragstart='return false'> -->
	    		<img src='/edmsimages/is#img#.png' class='doc_img_org' onclick="javascript:dataviewPreview('/edmsimages/il#img#.png');" onmouseleave="dataviewPreviewOut();" 
	    		onError="this.style.visibility='hidden'" onload="this.style.visibility='visible'" ondragstart='return false'>
		</div>
	
	
	   
	    <div>
	        <div class='doc_info'>
	            <div>
	                <span class='title'>자료명 :</span>
	                <span class='content' title="#doc_name#" style="white-space: nowrap; overflow: hidden;"> #doc_name# </span>
	            </div>
	            <div>
	                <span class='title'>자료번호 :</span>
	                <span class='content' title="#doc_no# (#rev_no#)" style="white-space: nowrap; overflow: hidden;"> #doc_no# (#rev_no#)</span>
	            </div>
	            <div>
	                <span class='title'>자료분류 :</span>
	                <span class='content' title="#tree_path#" style="white-space: nowrap; overflow: hidden;"> #tree_path# </span>
	            </div>
	        </div>
	        <div class='select_button' onclick="preview('#doc_seq#');">원본보기</div>
	    </div>
	</div>
	
	<!-- 한화 외주배포 팝업창 Start-->
	<div id="OutDistributeReqDiv" style="display: none;">
		<s:form id="OutDistributeReqFrm">
		<table class="wfFormTableCen" style="width:95%; height: 40%;">
			<tr>
				<td class="tdLabel" style="width:10%;">업체명</td>
				<td style="width:30%;">
					<s:hidden name="distributeMngDTO.company_id"/>
					<s:textfield name="distributeMngDTO.company_name" cssStyle="width:80%; border:1;" theme="simple"/>
					<input type="button" Style="width:15%;" value="..." class="button_dot" onClick="CompanyInfoSelect('T','OutDistributeReqFrm_distributeMngDTO_company_id', 'OutDistributeReqFrm_distributeMngDTO_company_name')"/>
				</td>
				<td style="width:10%;">
					<input type="button" Style="width:95%;" value="일괄적용" onClick="OutDistributeAllChange('company');"/>
				</td>
				<td class="tdLabel" style="width:10%;">만료일자</td>
				<td style="width:30%;">
					<s:textfield id="distribute_enddate" cssStyle="width:80%;" theme="simple"/>
					<s:hidden name="distributeMngDTO.distribute_enddate" />
					<span><img id="cal_distribute_enddate" border="0" src="<s:url value='/img/calendar.png'/>"></span>
				</td>
				<td style="width:10%;">
					<input type="button" Style="width:95%;" value="일괄적용" onClick="OutDistributeAllChange('date');"/>
				</td>
			</tr>
			<tr>
				<td class="tdLabel" style="width:10%;">다운로드 횟수</td>
				<td style="width:30%;">
					<s:textfield name="distributeMngDTO.download_cnt" cssStyle="width:98%; border:1;" theme="simple"/>
				</td>
				<td style="width:10%;">
					<input type="button" Style="width:95%;" value="일괄적용" onClick="OutDistributeAllChange('down');"/>
				</td>
				<td class="tdLabel" style="width:10%;">출력 횟수</td>
				<td style="width:30%;">
					<s:textfield name="distributeMngDTO.print_cnt" cssStyle="width:98%; border:1;" theme="simple"/>
				</td>
				<td style="width:10%;">
					<input type="button" Style="width:95%;" value="일괄적용" onClick="OutDistributeAllChange('print');"/>
				</td>
			</tr>
		</table>
		</s:form>
		<div id="OutDistributeReqGridbox" style="height:250px; width:95%; margin-left:10px; margin-top:5px;"></div>
		<div style="text-align:center; margin-top:15px;">	
				<input type="button" class="btn" style="width: 120px" onclick="OutDistributeReq();" value="배포" /> 
				<input type="button" class="btn" style="width: 120px" onclick="OutDistributeReqCancel();" value="취소" /> 
		</div>
	</div>	 
 	<!-- 한화 외주배포 팝업창 End-->
 	
 	<div class="search-cls topBtnArea" id="search" style="text-align: left;display: none; height: auto;">
		 
		 
	<span>
	<div id="combo_zone" style="display:inline-block;width:150px;margin-right:-12px;padding-left:2px;margin-top:-6px;"></div>
<%-- <select id="exceptTree" style="border-top-left-radius: 25px;height:36.2px;border-right:none;margin-right:-12px;
		  border-bottom-left-radius: 25px;width:10%;" mode="checkbox"></select> --%>
	<input id="foreground_text_input" style="width:65%;height:36px;  border-left:none;border-right:none;margin-right:-12px;
		 " type="text" name="" placeholder="검색어를 입력해 주세요." autocomplete="off">
<input id="magnifyingGlass" class="magnifyingGlass"
		  onmouseover="this.style.opacity='0.5';" onmouseout="this.style.opacity='1';"
		   type="button" onmouseover="this.style.cursor='pointer';" onclick="doSearch();">
	
		<select id="search_category" onmouseover="this.style.cursor='pointer';" class="sortOption" style="height:37px; margin-left:-5px;" onchange="imgClick(this)">
   				<option>보기 옵션</option>
			    <option id="grid_change" value="grid_change">Grid</option>
			    <option id="preview_change" value="preview_change">Image</option>
		</select>
		<input type="button" class="btn" style="margin-left:-5px;width:auto;height:37px; border-left: 1px solid #d6d6d6;" value="설정" onClick="toolChange('P');">
		<input type="button" class="btn" style="margin-left:-10px;width:auto;height:37px; border-left: 1px solid #d6d6d6;border-top-right-radius: 25px;border-bottom-right-radius: 25px;" value="검색도구" onClick="toolChange('S');">
			
	<!-- 	<input type="button" class="btn" style="margin-left:-7px;width:30;height:36px;border-top-right-radius: 25px;
				  border-bottom-right-radius: 25px;" value="검색"> -->
		<!-- <input style="margin-left:-5px;width:50px;height:36px;border-top-right-radius: 25px;
				  border-bottom-right-radius: 25px; background-image: url('/img/magnifyingGlass.PNG');
		  background-position: 5px center;
		  background-repeat: no-repeat; " type="text" value="검색"> -->
	</span>
		
	<span id="Search_tool">
		<span style="float:left;">
			<label class="switch">
			  <input type="checkbox" id="grayScale">
			  <span class="slider round"  title="IE에서는 흑백이 되지 않습니다."></span>
			</label>
			<span class="grayScale_toggle">원본</span><span class="grayScale_toggle" style="display:none;">흑백</span>
			
			<input type="checkbox" checked="checked" class="search_tool_checkbox"  id="rev_max" onchange="rev_max_checked();">
				<span class="labelTxt">최신자료 조회</span>
			
			<input type="checkbox" checked="checked" class="search_tool_checkbox" id="tree_sublevel" onchange="tree_sublevel_checked();">
				<span class="labelTxt">하위폴더 포함</span> 	
			
			<!-- 통계 list -->
				<select id="statistics_sort_list" class="sortOption2" onchange="doSearch()">
				    <option value="">통계 조회</option>
				    <option value="P">인쇄순</option>
				    <option value="V">조회순</option>
				    <option value="G">다운로드순</option>
				</select>
			<!-- end -->
				
			<!-- AND,OR 검색 조건 list -->
				<select id="and_or_list" class="sortOption2" onchange="doSearch()">
				    <option value="">조회 조건</option>
				    <option value="AND" selected>AND</option>
				    <option value="OR">OR</option>
				    <option value="EQUAL">EQUAL</option>
				</select>
			<!-- end -->
			 
			<!-- 갱신일 조건 list -->
			<select id="update_date_list" class="sortOption2" onchange="update_list_fnc('S')">
				    <option value="" selected>최종개정일</option>
				    <option value="1day">지난 1일</option>
				    <option value="1week">지난 1주</option>
				    <option value="1month">지난 1개월</option>
				    <option value="1year">지난 1년</option>
				    <option value="dateSetting" onclick="update_list_fnc('S')">기간 설정...</option>
			</select>
			<!-- end -->
			
			<!-- 즐겨찾기 list -->
			<input id="favorite_id" style="width:auto;background-image: url('/img/ico_arrow.png') ;
				  background-position: left center;
				  background-repeat: no-repeat; padding-left:40px;
				  border: none; margin-left: 30px;font-family: 'HDharmony';font-size: 13px;" type="text" name="" placeholder="즐겨찾기 리스트" autocomplete="off" >
		<!-- end -->
		</span>
	<!--    
			author : choihj
	   		date : 21-07-08
	   		comment : Search Grid Personal work(layout) 
	-->
	</span>
	<span id="Personal_tool" style="display:none;">
		<span style="float:left;">
			<input type="button" id="GridSettingButton" class="btn" style="margin-top: 5px;
				margin-left: 20px;" value="컬럼배열" onClick="gridSetting('SS','Y');">
		    <input type="button" id="GridSettingButton" class="btn" style="margin-top: 5px;
				margin-left: 20px;" value="설정 초기화" onClick="setting_reset('S')">
			
			<select id="number_of_view" class="sortOption2">
				    <option value="">조회건수 설정</option>
				    <option value="100">100 (조회건수)</option>
				    <option value="200">200 (조회건수)</option>
				    <option value="500">500 (조회건수)</option>
				    <option value="1000">1000 (조회건수)</option>
			</select>
		</span>
	</span>
		 <%-- <table>
		<tr>
		<td><b>갱신일 : </b></td>
			<td style="width:70%;">
				<s:textfield name="searchFormDTO.sDate" cssStyle="width:35%; border:1; font-size:15; text-align:center;" theme="simple" onkeyup="this.value = date_mask(this.value).replace(/[^0-9\-]/g,'');" maxlength='10'/>
				<span><img id="start_date" border="0" src="<s:url value='/img/calendar.png'/>"></span>
				<span style="display:inline-block;width:5%;text-align:center">~</span> 
				<s:textfield name="searchFormDTO.eDate" cssStyle="width:35%; border:1; font-size:15; text-align:center;" theme="simple" onkeyup="this.value = date_mask(this.value).replace(/[^0-9\-]/g,'');" maxlength='10'/>
				<span><img id="end_date" border="0" src="<s:url value='/img/calendar.png'/>"></span>
			</td>
			
			<td>
				<input id="favorite_id" style="width:auto;" type="text" name="" placeholder="즐겨찾기 리스트" autocomplete="off" >
			</td>
		</tr>
		</table>   --%>
	</div>
	
	<div id="markupListDiv" style="display:none">
	<input type="hidden" id="markup_target_doc" value="">
	<div id="markupgridbox" style="width: 800px; height: 500px"></div>
	</div>
	
	
	<!--    
			author : choihj
	   		date : 21-07-08
	   		comment : Search Grid Personal work(layout) 
	-->
	<div id="GridSettingDiv" style="width: 100%; height: 100%; display:none">
		<div id="GridSettingBox" style="width: 99%; height: 95%;"></div>
		<div id="GridSettingBox" style="width: 99%; height: 4%;">
			<B>* 컬럼의 순서는 Drag&Drop 으로 변경할 수 있습니다.</B>
		</div>
	</div>
	
	<div id="CalendarDiv" style="width: 100%; height: 100%; display:none;">
		<div id="Calendar">
		</div>
		<input type="button" class="btn" onclick="update_list_fnc('F')" value="적용">
	</div>
	
	<div id="ExcelDiv" style="width: 100%; height: 100%; display:none;">
		<p style="margin-top:10px">
		<%-- <select id="select_form" class="sortOption2" style="margin-top:10px;">
			    <option value="">Excel 양식 설정</option>
			    <option value="default_form">기본 양식</option>
			    <option value="personal_form">개인화 양식</option>
		</select> --%>
		<b>폼 선택</b>
		<br>
		<input type="checkbox" class="search_tool_checkbox"  id="default_form" onchange="excelSetting('F','D');" checked>
			<span class="labelTxt">기본 양식</span>
		<input type="checkbox" style="margin-left:46px;"class="search_tool_checkbox" id="personal_form"
		 onchange="excelSetting('F','P');">
			<span class="labelTxt">개인화 양식</span> 
		<!-- <input type="button" class="btn" style="margin-left:10px; width:auto;height:36px;" value="다운로드" onClick="excelDownload();"> -->
		</p>
		<hr/>
		<p style="margin-top:10px">
		<b>다운로드 방식 선택</b>
		<br>
		<input type="checkbox" class="search_tool_checkbox" id="standard" onchange="excelSetting('D','S');" checked>
			<span class="labelTxt">기준 다운로드</span>
		<input type="checkbox" class="search_tool_checkbox" id="all" onchange="excelSetting('D','A');">
			<span class="labelTxt">전체 기준다운</span> 
		</p>
		<hr/>
		<input type="button" class="btn" style="margin-left:10px; width:auto;height:36px;" value="다운로드" onClick="excelDownload();">
	</div>
	
	<div id="progressDiv" style="width:100%; height:100%; display: none; text-align:center;z-index: 9999;">
		<div class="prog">
        	<div class="progs" id="progressing">0%</div>
   		</div>
	</div>
	
	<!--  end -->
	<jsp:include page="/std/com/headJSP.jsp" />
</body>
</html>