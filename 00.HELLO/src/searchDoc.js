var gExcelParam = null;
var favoriteSelId = null;
var return_fid = "";
var return_attach_id = "";
var attachId = "";
var attachCnt = 0;
var attach_seq = 0;
var markup_wf_seq = 0;
var attachFiles = new Array();
var markupFiles = new Array();
var downloadWin;
var extChk;
var tooltipX;
var tooltipY;
var myPop;
var timer;
var SearchCnt = 0;
var myDataView;
var sId_array;
var dnd_obj;
var file_nm_prefix='';
var img_tooltip;
var dndChngTreeRole;
var dndRoleAlertCnt;
var dvContextMenu = null;
var confirmResult;
var failDownloadFile = '';
var markupgrid = null;
var markupPop;
var grayScaleClassNm = "";
/**
 * 2020.12.08(화) 이승훈
 * DataView 속성 값
 * DataView.getSelected() => 선택된 DataView의 ID 값(doc_seq)을 배열로 가져옴(한개만 선택했을 경우 String)
 * DataView.get(Id).lock_status(Property) => DataView Id 값의 Property 값
 * 한개만 선택한 경우 Id값을 배열이 아닌 스트링으로 가져오기 때문에 .size() UntypeError Exception
 * try - catch 문으로 한개인지 여러개인지 판단하려 했습니다.
 * 더 좋은 방법을 찾으시면 말씀 부탁드립니다. 
 */
dhtmlx.compat("dnd")
function doClearCookie(bGridclear) {
	_ses.empDTO
	Cookies.set("search", "");
	var inputs = document.body.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type == "radio")
			inputs[i].checked = inputs[i].defaultChecked;
	}
	/*var select = document.body.getElementsByTagName("select");
	for (var i = 0; i < select.length; i++) {
		select[i].value = "";
	}*/
	
	// 필터 삭제 후 필터검색 버그 수정
	mygrid.__filterParam=undefined;
	
	grid_select_rid = '';
	grid_select_cind = '';
	checkDetailSearch();
	if (bGridclear == undefined || bGridclear)
		mygrid.clearAll();
}
function gridboxonCollectValues() {
	window.setTimeout(HilightGrid, 10);
	return true;
}

function gridboxonPageChanged() {
	window.setTimeout(HilightGrid, 10);
	return true;
}

function HilightGrid() {
	var searchString = $('select_searchFormDTO_searchString').value;
	if (searchString == "")
		return;
	searchString = $w(searchString);
	for (var i = 0; i < mygrid.rowsBuffer.length; i++) {
		if (bHilightList.indexOf(i) != -1)
			continue;
		var row = mygrid.rowsBuffer[i];
		if (row == null)
			continue;
		if (row.getElementsByTagName == undefined || row.__bHilight)
			continue;
		row.__bHilight = true;
		var tds = row.getElementsByTagName("TD");
		for (var j = 0; j < tds.length; j++) {
			var td = tds[j];
			if (td.innerHTML.toLowerCase().startsWith("<img"))
				continue;
			for (var k = 0; k < searchString.length; k++) {
				var str = searchString[k];
				if (str.startsWith('#') || str.startsWith('!')
						|| str.startsWith('@'))
					str = str.substring(1);
				if (str.startsWith('%'))
					str = str.substring(1);
				str = str.trim();
				td.innerHTML = HightLight(td.innerHTML, str);
			}
		}
	}
}
function HightLight(org, hightstr) {
	if (hightstr == "")
		return org;
	var result = "";
	var pattern = RegExp(hightstr, "gi");
	var match = org.match(pattern);
	while (org.length > 0) {
		if (match = org.match(pattern)) {
			var index = org.indexOf(match[0]);
			result += org.slice(0, index);
			result += "<span class='highlight_str'>" + match[0] + "</span>";
			org = org.slice(index + match[0].length);
		} else {
			result += org, org = '';
		}
	}
	return result;
}
function ClearParameter(param) {
	var l = $H(param).clone();
	$H(param).each(function(a) {
		if (a.value == "")
			l.unset(a.key);
	});
	// l.unset('searchFormDTO.extcol');
	// l.unset('searchFormDTO.extcolid');
	// var extcol = $H(param).get('searchFormDTO.extcol');
	// var extcolid = $H(param).get('searchFormDTO.extcolid');
	var arr = new Array();
	// for ( var i = 0; i < extcol.length; i++) {
	// if (extcol[i] == "" || extcolid[i] == undefined)
	// continue;
	// var row = new Hash();
	// row.set('extend_col_code', extcolid[i]);
	// row.set('extend_col_value', extcol[i]);
	// arr.push(Object.toJSON(row));
	// }
	if (arr.length > 0)
		l.set('searchFormDTO.extcol.jsonString', arr);
	return l.toQueryString();
}

function doSearch(bid) {
	filterCheck = true; 
	
	SearchCnt++;
	var rev_max = document.getElementById("rev_max");
	if(rev_max.checked){
		$('select_searchFormDTO_revisionA').checked = false;
		$('select_searchFormDTO_revisionM').checked = true;
	}else{
		$('select_searchFormDTO_revisionA').checked = true;
		$('select_searchFormDTO_revisionM').checked = false;
	}
	if(buttonCheck == 1)
	{
		mygrid.enablePaging(true,seSearchCount("SC"),10,"page",true,"infoArea");
		mygrid.setPagingSkin("bricks")
	
		var param = null;
		/*$('Search_desc').style.display = "none";
		if (!$G('gridbox').isColumnHidden('result')) {
			$G('gridbox').setColumnHidden('result', true);
			$G('gridbox').setColumnValue('result', "");
	
		}*/
		
		if($('select_searchFormDTO_documentDTO_tree_id').value == ''){
			$('select_searchFormDTO_documentDTO_tree_id').value = 'T0';
			$('select_searchFormDTO_treeDTO_tname').value = mytree.getItemText('T0');
		}
		
		
		$('select_searchFormDTO_searchString').value = document.getElementById("foreground_text_input").value;
		$('select_searchFormDTO_autoSaveYN').value = j$('#autoSaveOff').css("display");
		$('select_searchFormDTO_andor').value = j$("#and_or_list option:selected").val();
		
		if($('select_searchFormDTO_andor').value == "")
		{
			$('select_searchFormDTO_andor').value = "AND";
		}
		
		$('select_searchFormDTO_statistics_sort').value = j$("#statistics_sort_list option:selected").val();
		
		$('select_searchFormDTO_page_uri').value = _URI;
		
		if ($('select_searchFormDTO_documentDTO_i_user_name').value == "") {
			$('select_searchFormDTO_documentDTO_i_user_id').value = "";
		}
		if ($('select_searchFormDTO_documentDTO_u_user_name').value == "") {
			$('select_searchFormDTO_documentDTO_u_user_id').value = "";
		}
		
		if(exceptList != null)
		{
			$('select_searchFormDTO_documentDTO_except_tree').value = exceptList;
		}
		
		/*
		 * //add20161128 if($('searchFormDTO.documentDTO.doc_no').value.indexOf(",") !=
		 * -1) { $('searchFormDTO.documentDTO.doc_no').value =
		 * $('searchFormDTO.documentDTO.doc_no').value.replace(/,/gi,"|"); } //end
		 */
		param = ClearParameter(Form.serialize('select', true));
		paramStr = param;
		Cookies.set("search", encodeURIComponent(param.gsub('+', ' ')));
	
		// 자료 조회 후 filter에 old_value 초기화를 추가하여 기존과 동일한 값 검색 시 검색이 되도록 수정
		for (var i = 0; i < mygrid.filters.size(); i++) {
			if (mygrid.filters[i][0].tagName == "INPUT")
				mygrid.filters[i][0].old_value = "";
				mygrid.filters[i][0].value="";
		}
		gExcelParam = param;
		paramStr = param;
		gridQString = _contextPath + "/edms/dc/seSearch.action?";
		mygrid.clearAndLoad(gridQString + param + "&searchFormDTO.gridNames="
				+ $G('gridbox').getIds() + "&emp_id=" + _ses.ses_emp_id,
				function() {				
					/*mygrid.forEachRow(function(id) {
						var updateDate = $G('gridbox').cells(id, 'update_date').getValue();
						$G('gridbox').cells(id, 'update_date').setValue(updateDate.replace(/\//gi, '-'));
					});*/
					doClearCookie(false);
					// checkDetailSearch();
					//PopWin.hide('searchDiv');
					// waiter.hide();
					
					addSearchDataList();
				});
		bHilightList = new Array();
		checkDetailSearch();
	
		if (bid == "search" || bid == undefined || bid.keyCode == "13") {
			var tid = $('select_searchFormDTO_documentDTO_tree_id').value;
			tid = (tid == "" ? "T0" : tid);
			// changeDocCnt(kindTree3, tid, "T");
			var dockind_id = $('select_searchFormDTO_documentDTO_dockind_id').value;
			dockind_id = (dockind_id == "" ? "K0" : dockind_id);
			// changeDocCnt(mytree, dockind_id, "K");
		}
	// waiter.show();
	}else{
		$('select_searchFormDTO_searchString').value = document.getElementById("foreground_text_input").value;
		$('select_searchFormDTO_autoSaveYN').value = j$('#autoSaveOff').css("display");
		$('select_searchFormDTO_andor').value = j$("#and_or_list option:selected").val();
		
		if($('select_searchFormDTO_andor').value == "")
		{
			$('select_searchFormDTO_andor').value = "AND";
		}
		
		$('select_searchFormDTO_statistics_sort').value = j$("#statistics_sort_list option:selected").val();
		
		if ($('select_searchFormDTO_documentDTO_i_user_name').value == "") {
			$('select_searchFormDTO_documentDTO_i_user_id').value = "";
		}
		if ($('select_searchFormDTO_documentDTO_u_user_name').value == "") {
			$('select_searchFormDTO_documentDTO_u_user_id').value = "";
		}
		
		var param = ClearParameter(Form.serialize('select', true));
		Cookies.set("search", encodeURIComponent(param.gsub('+', ' ')));
		
		var url = _contextPath + "/edms/dc/previewSearch.action?";
		new Ajax.Request(url, {
			method : 'POST',
			parameters : param,
			asynchronous : false,
			onSuccess : function(response) {
					var json = response.responseText;
						myDataView.clearAll();
						myDataView.parse(json,'json');
						
						addSearchDataList();
			}
		});
	}
}

function doDelete() {
	
	document.getElementById("upDeleteDoc").reset();
	
	var selectedId;
	
	if(buttonCheck == 1){
		
		selectedId = mygrid.getSelectedRowId();
		
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		selectedId = $w(selectedId.gsub(',', ' '));
		
		for (var i = 0; i < selectedId.size(); i++) {
			var lock_status = $G('gridbox').cells(selectedId[i], 'lock_status')
			.getValue();
			
			if (lock_status == 'T') {
				alert($L('SN_D236'));
				return;
			}
		}
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doDelete()
		 * DataView로 인한 기능 분기
		 */
		selectedId = myDataView.getSelected();				// DataView 선택된 Id값
		
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		
		try {
			
			for (var i = 0; i < selectedId.size(); i++) {		// 선택된 값이 한개일 경우 Array가 아닌 String 이기 때문에 UnTypeError Exception이 발생
				
				var data = myDataView.get(selectedId[i]);
				
				var lock_status = data.lock_status;
				
				if (lock_status == 'T') {
					alert($L('SN_D236'));
					return;
				}
			}
			
		} catch (e) {
			
			// 한개만 선택되었을 경우 처리
			var data = myDataView.get(selectedId);
			
			var lock_status = data.lock_status;
			
			if (lock_status == 'T') {
				alert($L('SN_D236'));
				return;
			}
		}
		
	}
	PopWin.show('deleteDocDiv');
	// PopWin.setText('deleteDocDiv', " [" + selectedId.size() + "] 건");
	PopWin.setStatus('deleteDocDiv', "자료 " + selectedId.size()
			+ $L("SN_D284", " 건이 선택되었습니다."));
	PopWin.w('deleteDocDiv').button("close").attachEvent("onClick",
			resultRemove);
}

function doChangeTree() {
	
	document.getElementById('upTreeAndKind').reset();
	
	var selectedId;
	
	if(buttonCheck == 1){
		
		selectedId = mygrid.getSelectedRowId();
		
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		selectedId = $w(selectedId.gsub(',', ' '));
		
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doChangeTree()
		 * DataView로 인한 기능 분기
		 */
		selectedId = myDataView.getSelected();					// 선택한 ID 값 조회(Array)
		
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		
	}

	PopWin.show('ChangeTreeDiv');
	// PopWin.setText('ChangeTreeDiv', "[" + selectedId.size() + "] 건");
	PopWin.setStatus('ChangeTreeDiv', selectedId.size()
			+ $L("SN_D284", " 건이 선택되었습니다."));
	PopWin.w('ChangeTreeDiv').button("close").attachEvent("onClick",
			resultRemove);
}
function doChange(kind) {
	if($('upTreeAndKind_docInforFormDTO_documentDTO_tree_id').value == '' && kind == 'tree'){
		alert("분류가 입력되지 않았습니다.");
		return false;
	}
	if($('upTreeAndKind_docInforFormDTO_documentDTO_dockind_id').value == '' && kind == 'kind'){
		alert("Area가 입력되지 않았습니다.");
		return false;
	}
	
	if(buttonCheck == 1){
		
		 swal({
				title : '',
				text : $L("SN_C015"),
				type : 'info',
				showCancelButton : true,
				confirmButtonClass : "btn-danger",
				confirmButtonText : "예",
				cancelButtonText : "아니오",
				closeOnConfirm : false,
				closeOnCancel : true
			}, function(isConfirm) {
				if (isConfirm) {
					var selectedId = mygrid.getSelectedRowId();
					if (selectedId == null) {
						alert($L('SN_D233'));
						return;
					}
					
					if($('upTreeAndKind_docInforFormDTO_documentDTO_chk_all_rev').value == 'ck'){
						
						$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq_list').value = selectedId;
						
						var url = _contextPath
						+ "/edms/dc/upTreeAndKind.action?docInforFormDTO.documentDTO.kind="
						+ kind; // 2014.2.14 수정
						var param = Form.serialize('upTreeAndKind');
						new Ajax.Request(
								url,
								{
									method : 'POST',
									parameters : param,
									asynchronous : false,
									onSuccess : function(response) {
										if (Number(response.responseText) > 0) {
											
											selectedId = $w(selectedId.gsub(',', ' '));
												selectedId.forEach(function(id) {
												if(kind == 'tree'){
													$G('gridbox').cells(id,'tree_id').setValue($('upTreeAndKind_docInforFormDTO_documentDTO_tree_name').value);
												}else if(kind == 'kind'){
													$G('gridbox').cells(id,'dockind_id').setValue($('upTreeAndKind_docInforFormDTO_documentDTO_dockind_name').value);
												}
											});
											
											
											alert('변경 되었습니다.');
											//doSearch();
											//PopWin.hide('ChangeTreeDiv');
										}else{
											alert('동일한 분류 내의 같은 자료번호 중 잠겨있는 항목이 발견되어 이동할 수 없습니다.');
											//PopWin.hide('ChangeTreeDiv');
										}
							}
						});
						
					}else{
						selectedId = $w(selectedId.gsub(',', ' '));
						
						var changeTree_Total = selectedId.size();
						var successCnt = 0;
						var failCnt = 0;
						var lockCnt = 0;
						
						mygrid.clearSelection();
						for (var i = 0; i < selectedId.size(); i++) {

							if ($G('gridbox').cells(selectedId[i], 'lock_status').getValue() == "T") {
								mygrid.selectRowById(selectedId[i], true, false, false);
								lockCnt++;
								if(changeTree_Total == (successCnt + failCnt + lockCnt)){
									alert("성공 : "+ successCnt + "\n실패 : "+failCnt + "\n잠겨있는 항목 : "+lockCnt);
									//doSearch();
									break;
								}
								continue;
								
							}
							var docseqno = $G('gridbox').cells(selectedId[i], 'doc_seq')
									.getValue();
							
							var targetId = $('upTreeAndKind_docInforFormDTO_documentDTO_tree_id').value;
							var drawing_no = $G("gridbox").cells(selectedId[i], "drawing_no").getValue();
							var doc_nm = $G("gridbox").cells(selectedId[i], "doc_name").getValue();
							var ext = $G("gridbox").cells(selectedId[i], "ext").getValue();
							ext = ext.substring(0,ext.length-1);
							var treeDocNo = getTreeDocNo(targetId);
							$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq').value = docseqno;
							
							if(drawing_no == ''){
								$('upTreeAndKind_docInforFormDTO_documentDTO_doc_no').value = treeDocNo + doc_nm+ "."+ext;
							}else{
								$('upTreeAndKind_docInforFormDTO_documentDTO_doc_no').value = treeDocNo + drawing_no + "."+ext;
							}
							
							$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq').value = docseqno;
							var url = _contextPath
									+ "/edms/dc/upTreeAndKind.action?docInforFormDTO.documentDTO.kind="
									+ kind; // 2014.2.14 수정
							var param = Form.serialize('upTreeAndKind');
							new Ajax.Request(
									url,
									{
										method : 'POST',
										parameters : param,
										asynchronous : false,
										onSuccess : function(response) {
											if (Number(response.responseText) > 0) {
												// 2014.2.14 수정 - start
												successCnt++;
												
												if(changeTree_Total == (successCnt + failCnt + lockCnt)){
													alert("성공 : "+ successCnt + "\n실패 : "+failCnt + "\n잠겨있는 항목 : "+lockCnt);
													//doSearch();
													//PopWin.hide('ChangeTreeDiv');
												}
												
											} else {
												failCnt++;
												
												if(changeTree_Total == (successCnt + failCnt + lockCnt)){
													alert("성공 : "+ successCnt + "\n실패 : "+failCnt + "\n잠겨있는 항목 : "+lockCnt);
													//doSearch();
													//PopWin.hide('ChangeTreeDiv');
												}
											}
										}
									});
						}
					}
					
				}else{
					
				}

			});
		
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doChange(kind)
		 * DataView로 인한 기능 분기
		 */
		
		 swal({
				title : '',
				text : $L("SN_C015"),
				type : 'info',
				showCancelButton : true,
				confirmButtonClass : "btn-danger",
				confirmButtonText : "예",
				cancelButtonText : "아니오",
				closeOnConfirm : false,
				closeOnCancel : true
			}, function(isConfirm) {
				if (isConfirm) {
					var selectedId = myDataView.getSelected();			// 선택한 Id 값 조회(다수 : Array / 한개 : String)
					if (selectedId == null) {
						alert($L('SN_D233'));
						return;
					}
					
					var changeTree_Total = selectedId.size();
					var successCnt = 0;
					var failCnt = 0;
					var lockCnt = 0;
					
					try{
						// 선택한 값이 다수 일 경우 
						changeTree_Total = selectedId.size();
						
						if($('upTreeAndKind_docInforFormDTO_documentDTO_chk_all_rev').value == 'ck'){
							
							$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq_list').value = selectedId.join(',');
							
							var url = _contextPath
							+ "/edms/dc/upTreeAndKind.action?docInforFormDTO.documentDTO.kind="
							+ kind; // 2014.2.14 수정
							var param = Form.serialize('upTreeAndKind');
							new Ajax.Request(
									url,
									{
										method : 'POST',
										parameters : param,
										asynchronous : false,
										onSuccess : function(response) {
											if (Number(response.responseText) > 0) {
												alert('변경 되었습니다.');
												//doSearch();
												PopWin.hide('ChangeTreeDiv');
											}else{
												alert('동일한 분류 내의 같은 자료번호 중 잠겨있는 항목이 발견되어 이동할 수 없습니다.');
												//PopWin.hide('ChangeTreeDiv');
											}
								}
							});
							
						}else{
							for (var i = 0; i < selectedId.size(); i++) {
								
								var data = myDataView.get(selectedId[i]);

								if (data.lock_status == "T") {
									lockCnt++;
									continue;
								}
								var docseqno = data.doc_seq
								$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq').value = docseqno;
								
								var targetId = $('upTreeAndKind_docInforFormDTO_documentDTO_tree_id').value;
								var drawing_no = data.drawing_no;
								var doc_nm = data.doc_name;
								var ext = data.ext;
								ext = ext.substring(0,ext.length-1);
								
								var treeDocNo = getTreeDocNo(targetId);
								$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq').value = docseqno;
								
								if(drawing_no == ''){
									$('upTreeAndKind_docInforFormDTO_documentDTO_doc_no').value = treeDocNo + doc_nm+ "."+ext;
								}else{
									$('upTreeAndKind_docInforFormDTO_documentDTO_doc_no').value = treeDocNo + drawing_no + "."+ext;
								}
								
								var url = _contextPath
										+ "/edms/dc/upTreeAndKind.action?docInforFormDTO.documentDTO.kind="
										+ kind; // 2014.2.14 수정
								var param = Form.serialize('upTreeAndKind');
								new Ajax.Request(
										url,
										{
											method : 'POST',
											parameters : param,
											asynchronous : false,
											onSuccess : function(response) {
												if (Number(response.responseText) > 0) {
													// 2014.2.14 수정 - start
													successCnt++;
													
													if(changeTree_Total == (successCnt + failCnt + lockCnt)){
													//	doSearch();
														alert("성공 : "+ successCnt + "\n실패 : "+failCnt + "\n잠겨있는 항목 : "+lockCnt);
														//PopWin.hide('ChangeTreeDiv');
													}
													
												} else {
													failCnt++;
													
													if(changeTree_Total == (successCnt + failCnt + lockCnt)){
													//	doSearch();
														alert("성공 : "+ successCnt + "\n실패 : "+failCnt + "\n잠겨있는 항목 : "+lockCnt);
														//PopWin.hide('ChangeTreeDiv');
													}
												}
											}
										});
							}
						}
						
						
						
					}catch (e) {
						// 선택한 값이 한개 일 경우 
						
						changeTree_Total = 1;
						if($('upTreeAndKind_docInforFormDTO_documentDTO_chk_all_rev').value == 'ck'){
							
							$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq_list').value = selectedId;
							
							var url = _contextPath
							+ "/edms/dc/upTreeAndKind.action?docInforFormDTO.documentDTO.kind="
							+ kind; // 2014.2.14 수정
							var param = Form.serialize('upTreeAndKind');
							new Ajax.Request(
									url,
									{
										method : 'POST',
										parameters : param,
										asynchronous : false,
										onSuccess : function(response) {
											if (Number(response.responseText) > 0) {
												alert('변경 되었습니다.');
												//doSearch();
												//PopWin.hide('ChangeTreeDiv');
											}else{
												alert('동일한 분류 내의 같은 자료번호 중 잠겨있는 항목이 발견되어 이동할 수 없습니다.');
												//PopWin.hide('ChangeTreeDiv');
											}
								}
							});
							
						}else{
							var data = myDataView.get(selectedId);

							if (data.lock_status == "T") {
								lockCnt++;
							}
							var docseqno = data.doc_seq
							$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq').value = docseqno;
							
							var drawing_no = data.drawing_no;
							var doc_nm = data.doc_name;
							var ext = data.ext;
							ext = ext.substring(0,ext.length-1);
							
							var treeDocNo = getTreeDocNo(targetId);
							$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq').value = docseqno;
							
							if(drawing_no == ''){
								$('upTreeAndKind_docInforFormDTO_documentDTO_doc_no').value = treeDocNo + doc_nm+ "."+ext;
							}else{
								$('upTreeAndKind_docInforFormDTO_documentDTO_doc_no').value = treeDocNo + drawing_no + "."+ext;
							}
							
							
							var url = _contextPath
									+ "/edms/dc/upTreeAndKind.action?docInforFormDTO.documentDTO.kind="
									+ kind; 
							var param = Form.serialize('upTreeAndKind');
							new Ajax.Request(
									url,
									{
										method : 'POST',
										parameters : param,
										asynchronous : false,
										onSuccess : function(response) {
											if (Number(response.responseText) > 0) {
												// 모든 작업이 끝난 후 Alert 을 통해 상황 알림
												successCnt++;
												
												if(changeTree_Total == (successCnt + failCnt + lockCnt)){
												//	doSearch();
													alert("성공 : "+ successCnt + "\n실패 : "+failCnt + "\n잠겨있는 항목 : "+lockCnt);
													//PopWin.hide('ChangeTreeDiv');
												}
												
											} else {
												failCnt++;
												
												if(changeTree_Total == (successCnt + failCnt + lockCnt)){
												//	doSearch();
													alert("성공 : "+ successCnt + "\n실패 : "+failCnt + "\n잠겨있는 항목 : "+lockCnt);
													//PopWin.hide('ChangeTreeDiv');
												}
											}
										}
									});
							}
						
						}
						
				}else{
					
				}

			});
		 
	}
	
}
function Delete() {
	
	if(buttonCheck == 1){
		
		 swal({
				title : '',
				text : $L("SN_C014"),
				type : 'info',
				showCancelButton : true,
				confirmButtonClass : "btn-danger",
				confirmButtonText : "예",
				cancelButtonText : "아니오",
				closeOnConfirm : true,
				closeOnCancel : true
			}, function(isConfirm) {
				if (isConfirm) {
					var selectedId = mygrid.getSelectedRowId();
					if (selectedId == null) {
						alert($L('SN_D233'));
						return;
					}
					if ($('upDeleteDoc_documentDTO_commts').value == "") {
						$('upDeleteDoc_documentDTO_commts').focus();
						alert($L('SN_D091'));
						return;
					}
					if ($G('gridbox').isColumnHidden('result')) {
						$G('gridbox').setColumnHidden('result', true);
					}
					selectedId = $w(selectedId.gsub(',', ' '));
					mygrid.clearSelection();
					var cnt = 0;
					var doc_seq_list = new Array();
					selectedId.each(function(rid) {
						if ($G('gridbox').cells(rid, 'lock_status').getValue() == "T") {
							mygrid.selectRowById(rid, true, false, false);
							$G('gridbox').cells(rid, 'result').setValue(
									"[lock]" + $L('W_FAIL'));
							return;
						}
						var docseqno = $G('gridbox').cells(rid, 'doc_seq').getValue();
						doc_seq_list.push(docseqno);
					});

					var url = _contextPath + "/edms/dc/upDeleteDoc.action";
					var param = Form.serialize('upDeleteDoc');
					param += "&documentDTO.doc_seq_list=" + doc_seq_list;

					new Ajax.Request(url, {
						method : 'POST',
						parameters : param,
						asynchronous : false,
						onSuccess : function(response) {
							if (response.responseText != "0") {
								this.closeOnConfirm = false;
								PopWin.hide('deleteDocDiv');
								alert($L('W_DELETE_COMPLETE'));

								doSearch();
							}
						}
					});

				}

			});

	}else{
		/**
		 * 2020.12.08(화)
		 * function : delete()
		 * DataView로 인한 기능 분기
		 */
		
		 swal({
				title : '',
				text : $L("SN_C014"),
				type : 'info',
				showCancelButton : true,
				confirmButtonClass : "btn-danger",
				confirmButtonText : "예",
				cancelButtonText : "아니오",
				closeOnConfirm : false,
				closeOnCancel : true
			}, function(isConfirm) {
				if (isConfirm) {
					var selectedId = myDataView.getSelected();			// 선택된 Id 값
					if (selectedId == null) {
						alert($L('SN_D233'));
						return;
					}
					if ($('upDeleteDoc_documentDTO_commts').value == "") {
						$('upDeleteDoc_documentDTO_commts').focus();
						alert($L('SN_D091'));
						return;
					}
					var lockCnt = 0;
					var cnt = 0;
					var doc_seq_list = new Array();
					
					try {
						// 다수선택 일 경우 
						var t = selectedId.size(); // 다수인지 체크
						
						selectedId.each(function(rid) {
							var data = myDataView.get(rid);
							if (data.lock_status == "T") {
								lockCnt++;
								return;
							}
							var docseqno = data.doc_seq;
							doc_seq_list.push(docseqno);
						});
						
					} catch (e) {
						
						// 한개 선택된 경우
						var data = myDataView.get(selectedId);
						if (data.lock_status == "T") {
							lockCnt++;
							return;
						}
						doc_seq_list.push(data.doc_seq);
					}

					var url = _contextPath + "/edms/dc/upDeleteDoc.action";
					var param = Form.serialize('upDeleteDoc');
					param += "&documentDTO.doc_seq_list=" + doc_seq_list;

					new Ajax.Request(url, {
						method : 'POST',
						parameters : param,
						asynchronous : false,
						onSuccess : function(response) {
							if (response.responseText != "0") {
								PopWin.hide('deleteDocDiv');
								alert($L('W_DELETE_COMPLETE'));

								doSearch();
							}
						}
					});
				}else{
					
				}

			});
	}
	
	
}

function fnToDate(cnt) {
	if (cnt != 30) {
		var today = new Date(); // 날자 변수 선언
		var dateNow = fnLPAD(String(today.getDate()), "0", 2); // 일자를 구함
		var monthNow = fnLPAD(String((today.getMonth() + 1)), "0", 2); // 월(month)을
		// 구함
		var yearNow = String(today.getYear()); // 년(year)을 구함
		return yearNow + "/" + monthNow + "/" + dateNow;
	} else {
		var today = new Date(); // 날자 변수 선언
		today.setDate(today.getDate() + 30);
		var dateNow = fnLPAD(String(today.getDate()), "0", 2); // 일자를 구함
		var monthNow = fnLPAD(String((today.getMonth() + 1)), "0", 2); // 월(month)을
		// 구함
		var yearNow = String(today.getYear()); // 년(year)을 구함
		return yearNow + "/" + monthNow + "/" + dateNow;
	}

}

function fnLPAD(val, set, cnt) {
	if (!set || !cnt || val.length >= cnt) {
		return val;
	}
	var max = (cnt - val.length) / set.length;

	for (var i = 0; i < max; i++) {
		val = set + val;
	}
	return val;
}
// end
function doInformation() {	
	if(buttonCheck ==1){
		var selectedId = mygrid.getSelectedRowId();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		selectedId = $w(selectedId.gsub(',', ' '));
		if (selectedId.length > 1) {
			
			 swal({
					title : '',
					text : "[" + $L("W_DATA_NAME") + ":"+ $G('gridbox').cells(selectedId[0], 'doc_name').getValue()	+ "]" + $L("TIP_DATA_INFO"),
					type : 'info',
					showCancelButton : true,
					confirmButtonClass : "btn-danger",
					confirmButtonText : "예",
					cancelButtonText : "아니오",
					closeOnConfirm : true,
					closeOnCancel : true
				}, function(isConfirm) {
					if (isConfirm) {
						selectedId = selectedId[0];
						
						var url = "";
						url = _contextPath + "/edms/dc/initDocInfor.action";
						
						var param = "doc_seq="+ $G('gridbox').cells(selectedId, 'doc_seq').getValue()+"&emp_id="+_ses.ses_emp_id;
						
						new Ajax.Request(url, {
							method : 'POST',
							parameters : param,
							asynchronous : false,
							onSuccess : function(response) {
									j$("#docInfoDiv").html(response.responseText);
							}
						});
						PopWin.show("docInfoDiv");
						PopWin.w('docInfoDiv').detachStatusBar();
						
					}else{
						return;
					}

				});
		}else{
			selectedId = selectedId[0];
			
			var url = "";
			url = _contextPath + "/edms/dc/initDocInfor.action";
			
			var param = "doc_seq="+ $G('gridbox').cells(selectedId, 'doc_seq').getValue()+"&emp_id="+_ses.ses_emp_id;
			
			new Ajax.Request(url, {
				method : 'POST',
				parameters : param,
				asynchronous : false,
				onSuccess : function(response) {
						j$("#docInfoDiv").html(response.responseText);
				}
			});
			
			PopWin.show("docInfoDiv");
			PopWin.w('docInfoDiv').detachStatusBar();
		}
		
		
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doInformation()
		 * DataView로 인한 기능 분기
		 */
		var selectedId = myDataView.getSelected();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		var data;
		try {
			// 다수 선택일 경우
			var t = selectedId.size();		// 다수 선택 구분
			data = myDataView.get(selectedId[0]);
			
			 swal({
					title : '',
					text : "[" + $L("W_DATA_NAME") + ":"+ data.doc_name	+ "]" + $L("TIP_DATA_INFO"),type : 'info',
					showCancelButton : true,
					confirmButtonClass : "btn-danger",
					confirmButtonText : "예",
					cancelButtonText : "아니오",
					closeOnConfirm : true,
					closeOnCancel : true
				}, function(isConfirm) {
					if (isConfirm) {
						var url = "";
						url = _contextPath + "/edms/dc/initDocInfor.action";
						
						var param = "doc_seq="+ data.doc_seq+"&emp_id="+_ses.ses_emp_id;
						
						new Ajax.Request(url, {
							method : 'POST',
							parameters : param,
							asynchronous : false,
							onSuccess : function(response) {
									j$("#docInfoDiv").html(response.responseText);
							}
						});
						
						PopWin.show("docInfoDiv");
						PopWin.w('docInfoDiv').detachStatusBar();
						
					}else{
						return;
					}

				});
			
		} catch (e) {
			data = myDataView.get(selectedId);
			
			var url = "";
			url = _contextPath + "/edms/dc/initDocInfor.action";
			
			var param = "doc_seq="+ data.doc_seq+"&emp_id="+_ses.ses_emp_id;
			
			new Ajax.Request(url, {
				method : 'POST',
				parameters : param,
				asynchronous : false,
				onSuccess : function(response) {
						j$("#docInfoDiv").html(response.responseText);
				}
			});
			
			PopWin.show("docInfoDiv");
			PopWin.w('docInfoDiv').detachStatusBar();
			
		}
		
		
		
		
	}
	

}

function CheckOut() {
	/**
	 * 2020.12.08(화)
	 * function : CheckOut()
	 * DataView로 인한 기능 분기
	 */
	var selectedId;
	if(buttonCheck == 1){
		selectedId = mygrid.getSelectedRowId();
	}else{
		selectedId = myDataView.getSelected();
	}
	
	if (selectedId == null) {
		alert($L('SN_D233'));
		return;
	}
	if ($('inCheckOutDoc_checkOutDTO_division_numberP').checked == true) {
		if ($('inCheckOutDoc_checkOutDTO_tree_name').value == '') {
			$('inCheckOutDoc_checkOutDTO_tree_name').focus();
			alert($L('SN_D091'));
			return;
		}
	}
	$('inCheckOutDoc_checkOutDTO_in_date').value = j$("#checkin_date").val();
	if ($('inCheckOutDoc_checkOutDTO_in_date').value == "") {
		$('inCheckOutDoc_checkOutDTO_in_date').focus();
		alert($L('SN_D091'));
		return;
	} else {// 입고예정일 입력시 오늘 이후의 일자만 입력 가능(희성정밀 시스템)
		var current = new Date();
		var selectedDate = $('inCheckOutDoc_checkOutDTO_in_date').value.replace(/-/gi, '');
		var year = current.getFullYear();
		var month = parseInt(current.getMonth() + 1) < 10 ? '0'
				+ (current.getMonth() + 1) : current.getMonth() + 1;
		var day = parseInt(current.getDate()) < 10 ? '0' + current.getDate()
				: current.getDate();
		current = year+"" + month+"" + day+"";
		var diff = parseInt(selectedDate) - parseInt(current);

		if (diff < 0) {
			alert($L('SN_ENTERING_DUE_INPUT') + " (" + year + "년 " + month
					+ "월 " + day + "일 이후)");
			return;
		}
	}
	
	var commt = $('inCheckOutDoc_checkOutDTO_commts').value;

	if (commt.length > 500) {
		$('inCheckOutDoc_checkOutDTO_commts').focus();
		alert($L('SN_COMMT_CHECK'));
		return;
	}

	var local_pathfile = $('inCheckOutDoc_checkOutDTO_checkout_path').value;
	if (local_pathfile == "") {
		$('inCheckOutDoc_checkOutDTO_checkout_path').focus();
		alert($L('SN_D286'));
		return;
	}
	
	if(buttonCheck == 1){
		
		downloadId = $w(selectedId.gsub(',', ' '));
	}else{
		// 다수 선택 시 배열이므로 gsub 에러
		downloadId = selectedId;
	}
	
	/*var chk = true;
	var url = _contextPath + "/edms/dc/seCheckOutDoc.action";
	var param = "checkOutDTO.doc_seq_list="+downloadId;
	new Ajax.Request(url,
			{
				method : 'POST',
				parameters : param,
				asynchronous : false,
				onSuccess : function(response) {
					if(response.responseText == "error"){
						alert("1.최신 도면이 아닌 문서가 포함되어 있습니다.\n2.삭제함에 최신문서가 존재합니다. 도면관리자에게 문의바랍니다.");
						//PopWin.hide('checkOutDiv');
						chk = false;
					}
				}
	});
	if(!chk){
		PopWin.hide('checkOutDiv');
		return;
	}*/
	
	if(buttonCheck == 1){
		if ($G('gridbox').isColumnHidden('result')) {
			$G('gridbox').setColumnHidden('result', true);
		}
		mygrid.clearSelection();

		downloadCnt = 0;
		downloadTotalCnt = downloadId.size();
		
		ProgressBarPlaying("START", downloadCnt, downloadTotalCnt);
		
		if ($G('gridbox').cells(downloadId[downloadCnt], 'lock_status').getValue() == "T") {
			mygrid.selectRowById(downloadId[downloadCnt], true, false, false);
			$G('gridbox').cells(downloadId[downloadCnt], 'result').setValue(
					$L('W_FAIL'));
			downloadCnt++;
			CallBackCheckOut();
			return;
		}
		
		Openfile.downloadFile(downloadId[downloadCnt], "O",$('inCheckOutDoc_checkOutDTO_checkout_path').value);
	}else{

		downloadCnt = 0;
		var data;
		try {
			// 다수 일 경우
			downloadTotalCnt = downloadId.size();
			data = myDataView.get(downloadId[downloadCnt]);
		} catch (e) {
			// 한개일 경우
			downloadTotalCnt = 1;
			data = myDataView.get(downloadId);
		}

		ProgressBarPlaying("START", downloadCnt, downloadTotalCnt);
		
		if (data.lock_status == "T") {
			downloadCnt++;
			CallBackCheckOut();
			return;
		}
		
		Openfile.downloadFile(data.doc_seq, "O",$('inCheckOutDoc_checkOutDTO_checkout_path').value);
	}
	
	
}
function CallBackCheckOut(data) {
	if (data.status.status != "S") {
		ProgressBarPlaying("ERROR", downloadCnt, downloadTotalCnt);
		alert("파일 다운로드 오류 발생("+data.status.errorcode+")");
		return;
	}else{
		
		if(buttonCheck == 1){
			
			var docseqno = $G('gridbox').cells(downloadId[downloadCnt-1], 'doc_seq').getValue();
			$('inCheckOutDoc_checkOutDTO_doc_seq').value = docseqno;

			var rev_no = $G('gridbox').cells(downloadId[downloadCnt-1], 'rev_no').getValue();
			$('inCheckOutDoc_checkOutDTO_rev_no').value = rev_no;
			
			var ext = $G('gridbox').cells(downloadId[downloadCnt-1], 'ext').getValue();
			var ext2 = ext.substring(0, ext.length - 1);
			if(ext2 == "com") $('inCheckOutDoc_checkOutDTO_ext').value = "dwg";
			else $('inCheckOutDoc_checkOutDTO_ext').value = ext2;
			
			
			var date_str = $('inCheckOutDoc_checkOutDTO_in_date').value.replace(/-/gi, '/');
			$('inCheckOutDoc_checkOutDTO_in_date').value=date_str;

			var param = Form.serialize('inCheckOutDoc');
			var url = _contextPath + "/edms/dc/inCheckOutDoc.action";
			new Ajax.Request(
					url,
					{
						method : 'POST',
						parameters : param,
						asynchronous : false,
						onSuccess : function(response) {
							if (response.responseText == "1") {
								
								var ext = $G('gridbox').cells(downloadId[downloadCnt-1], 'ext').getValue();
								ext = ext.substring(0, ext.length - 1) + "T";
								$G('gridbox').cells(downloadId[downloadCnt-1], 'ext').setValue(ext);
								$G('gridbox').cells(downloadId[downloadCnt-1],'lock_status').setValue("T");
								mygrid.___result(downloadId[downloadCnt-1],$L('W_SUCC'));
								
								
							}else if(response.responseText == "error"){
								alert("1.최신 도면이 아닌 문서가 포함되어 있습니다.\n2.삭제함에 최신문서가 존재합니다. 도면관리자에게 문의바랍니다.");
								PopWin.hide('checkOutDiv');
								return;
							}
						}
			});
			
		}else{
			
			try{
				var docseqno = myDataView.get(downloadId[downloadCnt-1]).doc_seq;
				$('inCheckOutDoc_checkOutDTO_doc_seq').value = docseqno;

				var rev_no = myDataView.get(downloadId[downloadCnt-1]).rev_no;
				$('inCheckOutDoc_checkOutDTO_rev_no').value = rev_no;
			}catch (e) {
				var docseqno = myDataView.get(downloadId).doc_seq;
				$('inCheckOutDoc_checkOutDTO_doc_seq').value = docseqno;

				var rev_no = myDataView.get(downloadId).rev_no;
				$('inCheckOutDoc_checkOutDTO_rev_no').value = rev_no;
			}
			

			var param = Form.serialize('inCheckOutDoc');
			var url = _contextPath + "/edms/dc/inCheckOutDoc.action";
			new Ajax.Request(
					url,
					{
						method : 'POST',
						parameters : param,
						asynchronous : false,
						onSuccess : function(response) {
							if (response.responseText == "1") {
								myDataView.refresh();
									
							}else if(response.responseText == "error"){
								alert("1.최신 도면이 아닌 문서가 포함되어 있습니다.\n2.삭제함에 최신문서가 존재합니다. 도면관리자에게 문의바랍니다.");
								PopWin.hide('checkOutDiv');
								return;
							}
						}
			});
			
		}
	}
	
	 if (downloadCnt == downloadTotalCnt) {
		    ProgressBarPlaying("FINAL", downloadCnt, downloadTotalCnt);
			alert($L("TIP_CHECKOUT_SUCCESS"));
			// PopWin.setStatus('checkOutDiv', $L('W_COMPLETE'));
			// resultRemove();
			PopWin.hide('checkOutDiv');
		}
	 else {
		nextActionCheck = true;
		
		 if(buttonCheck == 1){

				if ($G('gridbox').cells(downloadId[downloadCnt], 'lock_status').getValue() == "T") {
					mygrid.selectRowById(downloadId[downloadCnt], true, false, false);
					$G('gridbox').cells(downloadId[downloadCnt], 'result').setValue(
							$L('W_FAIL'));
					downloadCnt++;
					CallBackCheckOut();
					return;
				}
				ProgressBarPlaying("PLAY", downloadCnt, downloadTotalCnt);
				Openfile.downloadFile(downloadId[downloadCnt], "O",$('inCheckOutDoc_checkOutDTO_checkout_path').value);

				
			}else{

				var data = myDataView.get(downloadId[downloadCnt]);
				
				if (data.lock_status == "T") {
					downloadCnt++;
					CallBackCheckOut();
					return;
				}
				ProgressBarPlaying("PLAY", downloadCnt, downloadTotalCnt);
				Openfile.downloadFile(data.doc_seq, "O",$('inCheckOutDoc_checkOutDTO_checkout_path').value);
			}

	}
	
}

function doCheckOut() {
	
	document.getElementById("inCheckOutDoc").reset();
	//add_code 20211221 체크아웃 저장경로 조회
	let savePath = seMyPreference('CP');
	//end
	if(downloadPathCheck == "Y" && savePath != undefined)
	{
		 $('inCheckOutDoc_checkOutDTO_checkout_path').value = savePath;
	}
	let today = new Date();
	let year = today.getFullYear();
	let month = ('0' + (today.getMonth() + 1)).slice(-2);
	let day = ('0' + today.getDate()).slice(-2);
	dateString = year + '-' + month  + '-' + day;
	
	$('checkin_date').value = dateString;
		
	
	if(buttonCheck == 1){
		
		downloadStatus = "O";
		$('inCheckOutDoc_checkOutDTO_division_numberA').checked = true;
		RCheck(false);
		// add_code 20151215 VM환경 추가
		var url = _contextPath + "/edms/dc/getVMDate.action";
		new Ajax.Request(url, {
			method : 'POST',
			asynchronous : false,
			onSuccess : function(response) {
				var json = eval('(' + response.responseText + ')');
				vmIp = json.vMIp;
				vmCheck = json.vMCheck;
				vmCheckOut = json.vMCheckOut;
			}
		});

		var ip = _ses.ip.substr(0, _ses.ip.lastIndexOf("."));
		vmIp = vmIp.substr(0, vmIp.lastIndexOf("."));

		if (vmCheck == 'Y') {
			if (ip != vmIp) {
				if (vmCheckOut == 'Y') {
					// if(role_id != 3 ){
					alert("VM환경에서만 체크아웃이 가능합니다.");
					return;
					// }
				}
			}
		}
		// end

		var selectedId = mygrid.getSelectedRowId();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}

		selectedId = $w(selectedId.gsub(',', ' '));
		
		if(checkLock(selectedId) == false)
		{
			alert($L("SN_E1000"));
			return;
		}
		
		var url = _contextPath + "/edms/dc/seCheckOutDoc.action";
		var param = "checkOutDTO.doc_seq_list="+selectedId;
		new Ajax.Request(url,
				{
					method : 'POST',
					parameters : param,
					asynchronous : false,
					onSuccess : function(response) {
						if(response.responseText == "error"){
							alert("1.최신 도면이 아닌 문서가 포함되어 있습니다.\n2.삭제함에 최신문서가 존재합니다. 도면관리자에게 문의바랍니다.");
							return false;
						}else{
							var checkoutid = checkoutgrid.getAllRowIds();
							checkoutid = $w(checkoutid.gsub(',', ' '));
							checkoutid.each(function(rId) {
								if (rId.startsWith('_N')) {
									checkoutgrid.deleteRow(rId);
									return;
								} else {
									checkoutgrid.deleteRow(rId);
									return;
								}
							});
							for (var i = 0; i < selectedId.size(); i++) {
								var row = $G('gridbox10').defValue;
								var newID = $G('gridbox10').newID();
								row.set('ext', $G('gridbox').cells(selectedId[i], 'ext').getValue());
								//row.set('tree_id', $G('gridbox').cells(selectedId[i], 'tree_id').getValue());
								row.set('doc_no', $G('gridbox').cells(selectedId[i], 'doc_no').getValue());
								row.set('doc_name', $G('gridbox').cells(selectedId[i], 'doc_name').getValue());
								row.set('rev_no', $G('gridbox').cells(selectedId[i], 'rev_no').getValue());
								$G('gridbox10').addRow(newID, row);
								$G('gridbox10').cells(newID, 'ext').setValue($G('gridbox').cells(selectedId[i], 'ext').getValue());
							}

							$('inCheckOutDoc_checkOutDTO_commts').value = "";
							PopWin.show('checkOutDiv');
							// PopWin.setText('checkOutDiv', " [" + selectedId.size() + "] 건");
							PopWin.setStatus('checkOutDiv', selectedId.size()
									+ $L("SN_D284", " 건이 선택되었습니다."));
							PopWin.w('checkOutDiv').button("close")
									.attachEvent("onClick", resultRemove);
							
						}
					}
		});
		
	}else{
		
		/**
		 * 2020.12.08(화)
		 * function : doCheckOut()
		 * DataView로 인한 기능 분기
		 */
		
		downloadStatus = "O";
		$('inCheckOutDoc_checkOutDTO_division_numberA').checked = true;
		RCheck(false);
		// add_code 20151215 VM환경 추가
		var url = _contextPath + "/edms/dc/getVMDate.action";
		new Ajax.Request(url, {
			method : 'POST',
			asynchronous : false,
			onSuccess : function(response) {
				var json = eval('(' + response.responseText + ')');
				vmIp = json.vMIp;
				vmCheck = json.vMCheck;
				vmCheckOut = json.vMCheckOut;
			}
		});

		var ip = _ses.ip.substr(0, _ses.ip.lastIndexOf("."));
		vmIp = vmIp.substr(0, vmIp.lastIndexOf("."));

		if (vmCheck == 'Y') {
			if (ip != vmIp) {
				if (vmCheckOut == 'Y') {
					// if(role_id != 3 ){
					alert("VM환경에서만 체크아웃이 가능합니다.");
					return;
					// }
				}
			}
		}
		// end

		var selectedId = myDataView.getSelected();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}

		if(checkLock(selectedId) == false)
		{
			alert($L("SN_E1000"));
			return;
		}
		
		var checkoutid = checkoutgrid.getAllRowIds();
		checkoutid = $w(checkoutid.gsub(',', ' '));
		checkoutid.each(function(rId) {
			if (rId.startsWith('_N')) {
				checkoutgrid.deleteRow(rId);
				return;
			} else {
				checkoutgrid.deleteRow(rId);
				return;
			}
		});
		
		try {
			
			if(selectedId.size() > 1){
				var url = _contextPath + "/edms/dc/seCheckOutDoc.action";
				var param = "checkOutDTO.doc_seq_list="+selectedId.join(',');
				new Ajax.Request(url,
						{
							method : 'POST',
							parameters : param,
							asynchronous : false,
							onSuccess : function(response) {
								if(response.responseText == "error"){
									alert("1.최신 도면이 아닌 문서가 포함되어 있습니다.\n2.삭제함에 최신문서가 존재합니다. 도면관리자에게 문의바랍니다.");
									return false;
								}else{
									// 다수 선택 일 경우
									for (var i = 0; i < selectedId.size(); i++) {
										
									
										
										var data = myDataView.get(selectedId[i]);
										
										var row = $G('gridbox10').defValue;
										var newID = $G('gridbox10').newID();
										row.set('ext', data.ext);
										//row.set('tree_id', $G('gridbox').cells(selectedId[i], 'tree_id').getValue());
										row.set('doc_no', data.doc_no);
										row.set('doc_name', data.doc_name);
										row.set('rev_no', data.rev_no);
										$G('gridbox10').addRow(newID, row);
										$G('gridbox10').cells(newID, 'ext').setValue(data.ext);
									}
									
									$('inCheckOutDoc_checkOutDTO_commts').value = "";
									PopWin.show('checkOutDiv');
									// PopWin.setText('checkOutDiv', " [" + selectedId.size() + "] 건");
									PopWin.setStatus('checkOutDiv', selectedId.size()
											+ $L("SN_D284", " 건이 선택되었습니다."));
									PopWin.w('checkOutDiv').button("close")
											.attachEvent("onClick", resultRemove);
								}
							}
						});
			}
		
			
			
			
		} catch (e) {
			
			var url = _contextPath + "/edms/dc/seCheckOutDoc.action";
			var param = "checkOutDTO.doc_seq_list="+selectedId;
			new Ajax.Request(url,
					{
						method : 'POST',
						parameters : param,
						asynchronous : false,
						onSuccess : function(response) {
							if(response.responseText == "error"){
								alert("1.최신 도면이 아닌 문서가 포함되어 있습니다.\n2.삭제함에 최신문서가 존재합니다. 도면관리자에게 문의바랍니다.");
								return false;
							}else{
								// 한개만 션택한 경우
								var data = myDataView.get(selectedId);
								
								var row = $G('gridbox10').defValue;
								var newID = $G('gridbox10').newID();
								row.set('ext', data.ext);
								//row.set('tree_id', $G('gridbox').cells(selectedId[i], 'tree_id').getValue());
								row.set('doc_no', data.doc_no);
								row.set('doc_name', data.doc_name);
								row.set('rev_no', data.rev_no);
								$G('gridbox10').addRow(newID, row);
								$G('gridbox10').cells(newID, 'ext').setValue(data.ext);
								
								$('inCheckOutDoc_checkOutDTO_commts').value = "";
								PopWin.show('checkOutDiv');
								// PopWin.setText('checkOutDiv', " [" + selectedId.size() + "] 건");
								PopWin.setStatus('checkOutDiv', '1'
										+ $L("SN_D284", " 건이 선택되었습니다."));
								PopWin.w('checkOutDiv').button("close")
										.attachEvent("onClick", resultRemove);
								
							}
						}
					});
		}

	}

}

function kindtreeBoxonSelect(id) {
	var text = $L('W_KIND') + " : "
			+ $('select_searchFormDTO_documentDTO_dockind_name').value;
	text = convert(text);
	// text = text.split(' ').join('&nbsp;') ;
	// Accordion.setText("b", text);

	// doSearch(id);
	// kindTree3.openItem(id);

	// 2016.10.10 Kijoo Kim 건수 변경 적용 메서드 추가
	var dockind_id = $('select_searchFormDTO_documentDTO_dockind_id').value;
	// changeDocCnt(mytree, dockind_id, "K");
}

/*
 * 2014.09.26 WooChul Jung Customer demands that the tree should be like window
 * explorer. when user select folder, then the files under that folder is
 * visible without open sub-folder. when user db-click folder, then the files
 * under that folder is visible with opeming sub-folder.
 */
function kindtreeBoxonDblClick(id) {
	var text = $L('W_KIND') + " : "
			+ $('select_searchFormDTO_documentDTO_dockind_name').value;
	text = convert(text);
	// text = text.split(' ').join('&nbsp;') ;
	// Accordion.setText("b", text);

	doSearch(id);
	kindTree.openItem(id);

	// 2016.10.10 Kijoo Kim 건수 변경 적용 메서드 추가
	var dockind_id = $('select_searchFormDTO_documentDTO_dockind_id').value;
	// changeDocCnt(mytree, dockind_id, "K");

}

function kindtreeBoxonLoad() {
	if (kindTree != undefined
			&& $('select_searchFormDTO_documentDTO_dockind_id').value != "") {
		kindTree
				.openItem($('select_searchFormDTO_documentDTO_dockind_id').value);
		kindTree
				.selectItem($('select_searchFormDTO_documentDTO_dockind_id').value);
	}
}
function treeBoxonSelect(id) {
	var text = $L('W_TREE') + " : "
			+ $('select_searchFormDTO_treeDTO_tname').value;
	text = convert(text);
	// text = text.split(' ').join('&nbsp;') ;
	// Accordion.setText("a", text);

	// doSearch(id);
	// mytree.openItem(id);

	// 2016.10.10 Kijoo Kim 자료종류 건수 적용 추가
	var tid = $('select_searchFormDTO_documentDTO_tree_id').value;
	// changeDocCnt(kindTree3, tid, "T");
}

/*
 * 2014.09.26 WooChul Jung Customer demands that the tree should be like window
 * explorer. when user select folder, then the files under that folder is
 * visible without open sub-folder. when user db-click folder, then the files
 * under that folder is visible with opeming sub-folder.
 */
function treeBoxonDblClick(id) {
	var text = $L('W_TREE') + " : "
			+ $('select_searchFormDTO_treeDTO_tname').value;
	text = convert(text);
	// text = text.split(' ').join('&nbsp;') ;
	// Accordion.setText("a", text);
	
	doSearch(id);
	
	mytree.openItem(id);

	// 2016.10.10 Kijoo Kim 자료종류 건수 적용 추가
	var tid = $('select_searchFormDTO_documentDTO_tree_id').value;
	// changeDocCnt(kindTree3, tid, "T");
}

function treeBoxonLoad() {
	if ($('select_searchFormDTO_documentDTO_tree_id').value != "") {
		mytree.openItem($('select_searchFormDTO_documentDTO_tree_id').value);
		mytree.selectItem($('select_searchFormDTO_documentDTO_tree_id').value);
	}
}

function doDetailSearch() {
	PopWin.show('searchDiv');
	PopWin.w('searchDiv').button("close").attachEvent("onClick", function() {
		doClearCookie(false);
		checkDetailSearch();
		j$("#select")[0].reset();
	});

}

function checkDetailSearch() {
	if ($('select_searchFormDTO_documentDTO_tree_id').value != "") {
		// mytree.openItem($('select_searchFormDTO_documentDTO_tree_id').value);
		mytree.selectItem($('select_searchFormDTO_documentDTO_tree_id').value);
	}

	if ($('select_searchFormDTO_documentDTO_doc_type').checked != true
			|| $('select_searchFormDTO_documentDTO_com_doc_no').value != ""
			|| $('select_searchFormDTO_documentDTO_grade_id').value != ""
			|| $('select_searchFormDTO_documentDTO_doc_no').value != ""
			|| $('select_searchFormDTO_item').value != ""
			|| $('select_searchFormDTO_documentDTO_doc_name').value != ""
			|| $('select_searchFormDTO_documentDTO_maintain_id').value != ""
			|| $('select_searchFormDTO_documentDTO_project_name').value != ""
			|| $('select_searchFormDTO_documentDTO_i_user_id').value != ""
			|| $('select_searchFormDTO_documentDTO_u_user_id').value != ""
			|| $('select_searchFormDTO_documentDTO_lock_statusA').checked != true
			|| $('select_searchFormDTO_documentDTO_ext').value != ""
			|| $('select_searchFormDTO_revisionM').checked != true
			|| $('select_searchFormDTO_documentDTO_lock_statusA').checked != true
			|| $('select_searchFormDTO_documentDTO_commts').value != "") {
		toolbar.___setItemState('detailSearch', true);
		return;
	}
	toolbar.___setItemState('detailSearch', false);
}
var mytree = "";
var gridColNames = null;
var mygrid = null;
var selectRowId;


function doPrint(data) {
	
	 swal({
			title : '보안경고',
			text : _securityAlertMessage,
			type : 'warning',
			showCancelButton : true,
			confirmButtonClass : "btn-danger",
			confirmButtonText : "동의",
			cancelButtonText : "동의안함",
			customClass : 'swal_security_waring',
			closeOnConfirm : true,
			closeOnCancel : false
		}, function(isConfirm) {
			if (isConfirm) {
				var selectId;
				
				if(buttonCheck == 1){
					selectId = mygrid.getSelectedRowId();
					printCnt = 0;
					if (selectId == null) {
						this.closeOnConfirm = false;
						alert($L('SN_D233'));
						return;
					}
					selectId = $w(selectId.gsub(',', ' '));
					
					
					for (var i = 0; i < selectId.size(); i++) {
						var ext = $G('gridbox').cells(selectId[i], 'ext').getValue();
						if (!isRapidViewUsedExt(ext.substring(0,Number(ext.length-1)))) {
							this.closeOnConfirm = false;
							alert($L('SN_NOT_SUPPORT_EXTENSION'));
							return;
						}
					}
					PopWin.hide("PrintPopUpDiv2");

					var doc_seq_list="";
					for (var i = 0; i < selectId.size(); i++) {
						var doc_seq = $G('gridbox').cells(selectId[i],'doc_seq').getValue();
						if(i==0) doc_seq_list = doc_seq;
						else doc_seq_list = doc_seq_list + "," + doc_seq;
					}

				}else{
					
					/**
					 * 2020.12.08(화)
					 * function : doPrint(data)
					 * DataView로 인한 기능 분기
					 */
					
					selectId = myDataView.getSelected();
					printCnt = 0;
					if (selectId == null) {
						alert($L('SN_D233'));
						return;
					}
					
					try {
						// 다수 일 경우
						for (var i = 0; i < selectId.size(); i++) {
							
							var data = myDataView.get(selectId[i]);
							
							var ext = data.ext;
							if (!isRapidViewUsedExt(ext.substring(0,Number(ext.length-1)))) {
								this.closeOnConfirm = false;
								alert($L('SN_NOT_SUPPORT_EXTENSION'));
								return;
							}
						}
						
						PopWin.hide("PrintPopUpDiv2");

						var doc_seq_list="";
						for (var i = 0; i < selectId.size(); i++) {
							
							var doc_seq = myDataView.get(selectId[i]).doc_seq;
							
							if(i==0) doc_seq_list = doc_seq;
							else doc_seq_list = doc_seq_list + "," + doc_seq;
						}
						
					} catch (e) {
						
						// 한개 션택일 경우
						var data = myDataView.get(selectId);
						
						var ext = data.ext;
						if (!isRapidViewUsedExt(ext.substring(0,Number(ext.length-1)))) {
							this.closeOnConfirm = false;
							alert($L('SN_NOT_SUPPORT_EXTENSION'));
							return;
						}
						
						PopWin.hide("PrintPopUpDiv2");

						var doc_seq_list="";
							
						var doc_seq = myDataView.get(selectId).doc_seq;
						
						doc_seq_list = doc_seq;
					}
					
				}

				var gridQString = _contextPath + "/edms/dc/sePrintList.action?";
				PrintPopUpgrid.clearAndLoad(gridQString + "&outSourcingDTO.doc_seq_list=" + doc_seq_list
						+ "&outSourcingDTO.gridNames=" + $G('PrintPopUpgridbox').getIds(),null		
				);
				
				PopWin.show("PrintPopUpDiv2");
				PopWin.w('PrintPopUpDiv2').button("close").attachEvent("onClick",function(){		
				});
				
			}else{
				alert('동의하지 않으시면 출력을 하실 수 없습니다.');
			}
		});
			
	
	
}

function ChangeList1(type){
	var rId = PrintPopUpgrid.getSelectedRowId();
	if(rId == null){
		alert("파일을 선택바랍니다.");
		return;
	}
	if(type == "UP"){
		PrintPopUpgrid.moveRowUp(rId);
	}else{
		PrintPopUpgrid.moveRowDown(rId);
	}
}

function print(){
	var rId = PrintPopUpgrid.getAllRowIds();
	rId = $w(rId.gsub(',', ' '));
	
	var doc_seq_list="";
	for (var i = 0; i < rId.size(); i++) {
		var doc_seq = $G('PrintPopUpgridbox').cells(rId[i],'doc_seq').getValue();
		if(i==0) doc_seq_list = doc_seq;
		else doc_seq_list = doc_seq_list + "," + doc_seq;
	}
	
	_printDocSeqList = doc_seq_list;
	
	selectId = $w(doc_seq_list.gsub(',', ' '));
	var param = "?socketDTO.doc_seqno_list=" + selectId+"&socketDTO.auth_chk=Y";
	var url = _contextPath + "/edms/socket/seDocumentPrint.action";
	new Ajax.Request(url, {
		method : 'POST',
		parameters : param.toQueryParams(),
		asynchronous : false,
		onSuccess : function(response) {
			if(response.responseText == "")
			{
				alert($L('SN_AUTH_DOC_PRINT'));
				return;
			}
			var json = eval('(' + response.responseText + ')');
			printId = json;
			printTotalCnt = json.length;
			nextPrintChk = json[printCnt].ext_check;
			DocumentPrint(json, printCnt, printTotalCnt);
			
		}
	});
}

function CallBackPrint(responseData) {
	
	alert("출력을 완료하였습니다.");
	/*
	if (nextPrintChk != 1) {
		if (pdfCheck == true) {
			// 전체 건수, 출력 된 건수, 실패 건수로 메시지 변경하기
		}
		pdfCheck = false;
		PopWin.hide("PrintPopUpDiv2");
	}else{
		var param = "?socketDTO.doc_seqno_list=" + selectId +"&socketDTO.ext_check="+2;
		var url = _contextPath + "/edms/socket/seDocumentPrint.action";
		new Ajax.Request(url, {
			method : 'POST',
			parameters : param.toQueryParams(),
			asynchronous : false,
			onSuccess : function(response) {
				var json = eval('(' + response.responseText + ')');
				printId = json;
				printCnt = 0;
				printTotalCnt = json.length;
				nextPrintChk = json[printCnt].ext_check;
				DocumentPrint(json, printCnt, printTotalCnt);
				
			}
		});
	}*/
}

function validateApprover(approvers) {
	if ((approvers == null) || (approvers.size() == 0))
		return false;
	for (var i = 0; i < approvers.size(); i++) {
		var obj = approvers[i];
		if ((obj.value == null) || (obj.value == '')) {
			return false;
		}
	}
	return true;
}

function isInteger(number){
	return number % 1 === 0;
}

function reqPrint() {

	if (!notNullCheck('inReqPrint_workFlowFormDTO_workflowDTO_emp_name',
			$L('SN_SE_WORKER'))) {
		return;
	}
	if (!notNullCheck('inReqPrint_workFlowFormDTO_workflowDetailDTO_req_size')) {
		return;
	}
	if (!notNullCheck('inReqPrint_workFlowFormDTO_workflowDetailDTO_req_count')) {
		return;
	}
	//2021-10-05 Number.isInteger() -> IE에서 지원안됨
	var num = $('inReqPrint_workFlowFormDTO_workflowDetailDTO_req_count').value;
	
	if(isInteger(num)){
		if(num.indexOf(".") >=0){
			alert("매수는 숫자만 입력가능합니다.");
			return;
		}
	}else{
		alert("매수는 숫자만 입력가능합니다.");
		return;
	}
	
	 swal({
			title : '',
			text : $L("SN_C018"),
			type : 'info',
			showCancelButton : true,
			confirmButtonClass : "btn-danger",
			confirmButtonText : "예",
			cancelButtonText : "아니오",
			closeOnConfirm : false,
			closeOnCancel : true
		}, function(isConfirm) {
			if (isConfirm) {
				
				if (validateApprover($('inReqPrint').IdObs) == false) {
					alert($L('SN_SE_APP'));
					return;
				}
				var url = _contextPath + "/edms/wf/Request.action";
				var param = Form.serialize('inReqPrint');

				/*
				 * 2015.06.22 WooChul Jung. TIPS use Groupware for enrollment approval.
				 */
				// param = param + "&workFlowFormDTO.appList.app_user=" + _ses.empDTO.emp_id
				// ;
				new Ajax.Request(url, {
					method : 'POST',
					parameters : param,
					asynchronous : false,
					onSuccess : function(response) {
						var json = eval('(' + response.responseText + ')');
						if (json.workflowDTO != undefined
								&& json.workflowDTO.work_seq != undefined) {
							alert($L('SN_I023'));
							
						}
					}
				});
				
				for(var a=0; a<$('inReqPrint').createSequnceNum; a++)
				{
					 $('inReqPrint').RemoveTr();
				}
				PopWin.setStatus('ReqPrintDiv', $L('W_COMPLETE'));
				PopWin.hide('ReqPrintDiv');
			}else{
				
			}

		});

	
}


function doRequestPrint() {
	PopWin.hide('ReqChangeDiv');
	/*
	 * if (preViewDiv) preViewDiv.hide();
	 */
	document.getElementById("inReqPrint").reset();
	var selectedId;
	var doc_seqs = "";
	
	if(buttonCheck == 1){
		selectedId = mygrid.getSelectedRowId();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}

		selectedId = $w(selectedId.gsub(',', ' '));
	

		for (var i = 0; i < selectedId.size(); i++) {
			if (i != 0)
				doc_seqs += ",";
			doc_seqs += $G('gridbox').cells(selectedId[i], 'doc_seq').getValue();
		}

	}else{
		/**
		 * 2020.12.08(화)
		 * function : doRequestPrint()
		 * DataView로 인한 기능 분기
		 */
		
		selectedId = myDataView.getSelected();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}

		try {
			// 다수 선택일 경우
			for (var i = 0; i < selectedId.size(); i++) {
				if (i != 0)
					doc_seqs += ",";
				doc_seqs += myDataView.get(selectedId[i]).doc_seq;
			}
		} catch (e) {
			// 한개 션택일 경우
			doc_seqs = myDataView.get(selectedId).doc_seq;
		}
		
		
	}
	$('inReqPrint_workFlowFormDTO_doc_seq').value = doc_seqs;

	var gridQString = _contextPath + "/edms/dc/seRPDocList.action?";
	RPgrid.clearAndLoad(gridQString + "&searchFormDTO.doc_seq_list=" + doc_seqs
			+ "&searchFormDTO.gridNames3=" + $G('ReqPrintGridDiv').getIds(),
			null);

	$('inReqPrint').createSequnceNum = 0;
	PopWin.show('ReqPrintDiv');
	
	$('inReqPrint').AddTr('Print');
	// PopWin.setText('ReqPrintDiv', " [" + selectedId.size() + "] 건");
	PopWin.setStatus('ReqPrintDiv', selectedId.size()
			+ $L("SN_D284", " 건이 선택되었습니다."));
	

	PopWin.w('ReqPrintDiv').button("close").attachEvent("onClick", function() {
		for(var a=0; a<$('inReqPrint').createSequnceNum; a++)
		{
			 $('inReqPrint').RemoveTr();
		}
		downloadWin.hide();
		downloadWin.setModal(false);
		console.log($('inReqPrint').createSequnceNum);
	});
	
}
function download(kind) {
	var selectedId = DownloadPopUpgrid.getAllRowIds();

	if (selectedId == null) {
		alert($L('SN_D233'));
		return;
	}
	var local_pathfile = $('inDownloadDoc_downloadDocFormDTO_out_path').value;
	if (local_pathfile == "" && kind != "PD") {
		alert($L('SN_D286'));
		$('inDownloadDoc_downloadDocFormDTO_out_path').focus();
		return;
	}

	var commtsList = j$(':radio[name="commtsList"]:checked').val();
	if(commtsList === "기타(다른사유 작성)"){
		$('inDownloadDoc_downloadDocFormDTO_commts').value = j$("#etcCommts").val();
	}else{
		$('inDownloadDoc_downloadDocFormDTO_commts').value = commtsList;
	}
	if (!notNullCheck('inDownloadDoc_downloadDocFormDTO_commts')) {
		return;
	}

	if ($G('gridbox').isColumnHidden('result')) {
		$G('gridbox').setColumnHidden('result', true);
	}
	
	downloadId = $w(selectedId.gsub(',', ' '));
	downloadCnt = 0;
	downloadTotalCnt = downloadId.size();
	
	ProgressBarPlaying("START", downloadCnt, downloadTotalCnt);
	
	//kind = D(원본 다운로드), PD(PDF 다운로드)
	if(kind == undefined)
	{
		downloadStatus = "D";
		kind = "D";
	}else{
		downloadStatus = "PD";
		kind=="PD";
		
		PDFDownload(0,function(){});
		return;
	}		
	
	if($('inDownloadDoc_downloadDocFormDTO_file_nm_prefix').value != ''){
		if(downloadTotalCnt > 1){
			var file_nm_no =  Number(downloadCnt)+1;
			file_nm_prefix = $('inDownloadDoc_downloadDocFormDTO_file_nm_prefix').value + "_" + file_nm_no;
		}else{
			file_nm_prefix = $('inDownloadDoc_downloadDocFormDTO_file_nm_prefix').value;
		}
		
	}
	
	Openfile.downloadFile(downloadId[downloadCnt], kind,
			$('inDownloadDoc_downloadDocFormDTO_out_path').value,null,file_nm_prefix);

}


fnCreateIframe = function (name)
{
  var frm = j$('<iframe name="' + name + '" style="display: none;"></iframe>');
  frm.appendTo("body");
}
PDFDownload = function(i,callback) {
    setTimeout(function() {
        if (i < downloadTotalCnt) {
			let url = _contextPath + "/edms/interfaces/downloadfileWithDocseq.action";
			let doc_seq = downloadId[i];
			let monochrome_yn = $G('DownloadPopUpgridbox').cells(downloadId[i],'monochrome_yn').getValue();
			let param = "isPDF=true&doc_seq="+doc_seq+"&isMONO="+monochrome_yn;
		
			fnCreateIframe(i);
			
			var win = window.open("",i,"_blank");
			//win.document.open();
			win.document.write("<html><body>");
			win.document
					.write('<form id="myform" method="post" action="'
							+ url
							+ '" accept-charset="utf-8" enctype="text/html"><input type="hidden" name="isPDF" id="isPDF" value="true"/>'
							+ '<input type="hidden" name="doc_seq" id="doc_seq"/> <input type="hidden" name="isMONO" id="isMONO"/>'
							+ ' </form>');
			win.document.getElementById("doc_seq").value = doc_seq;
			win.document.getElementById("isMONO").value = monochrome_yn;
			
			win.document.write("</body></html>");
			win.document.getElementById("myform").submit();
			win.document.close();
			var Url = _contextPath + "/edms/ht/inWorkHistory.action?";
				param = "historyFormDTO.documentDTO.doc_seq="
								+ doc_seq
								+ "&historyFormDTO.workhistoryDTO.kind=G"
								+ "&historyFormDTO.documentDTO.commts=" + $('inDownloadDoc_downloadDocFormDTO_commts').value;
						new Ajax.Request(Url, {
							method : 'POST',
							parameters : param,
							asynchronous : false,
							onSuccess : function(response) {
								j$(i).remove();
								ProgressBarPlaying("PLAY", i+1, downloadTotalCnt);
								PDFDownload(i+1,callback);
							}
						});
        } else {
        	ProgressBarPlaying("FINAL", i, downloadTotalCnt);
        	PopWin.hide('downloadDiv');
			alert("다운로드가 완료되었습니다.");
			PopWin.setStatus('downloadDiv', $L('W_COMPLETE'));
            callback();
        }
    }, 1000);
}


//PDF병합
function PAbsor(){
	
	var DownloadIds = DownloadPopUpgrid.getAllRowIds();
	var merger = new Array();
	var pdf_NCount = 0;
	
	var chk_pdf_yn_ids = DownloadIds.split(',');
	
	for(var i=0; i<chk_pdf_yn_ids.size(); i++){
		var orgPdfYn =  $G('DownloadPopUpgridbox').cells(chk_pdf_yn_ids[i],'ext').getValue().toLowerCase();
		if(DownloadPopUpgrid.cells(chk_pdf_yn_ids[i],4).getValue() == 'Y' 
			|| orgPdfYn.indexOf('pdf') != -1){
			merger.push(chk_pdf_yn_ids[i]);
		}else{
			pdf_NCount++;
		}
	}
	
	if(merger.size() < 1){
		alert("모든파일의 PDF가 없습니다.")
		return false;
	}else{
		DownloadIds = merger.join(',');
	}
	
	var commtsList = j$(':radio[name="commtsList"]:checked').val();
	if(commtsList === "기타(다른사유 작성)"){
		$('inDownloadDoc_downloadDocFormDTO_commts').value = j$("#etcCommts").val();
	}else{
		$('inDownloadDoc_downloadDocFormDTO_commts').value = commtsList;
	}
	if (!notNullCheck('inDownloadDoc_downloadDocFormDTO_commts')) {
		return;
	}
	if (!notNullCheck('inDownloadDoc_downloadDocFormDTO_commts')) {
		return;
	}
	

	if($('inDownloadDoc_downloadDocFormDTO_file_nm_prefix').value != ''){
		file_nm_prefix = $('inDownloadDoc_downloadDocFormDTO_file_nm_prefix').value;
	}
	
	
	if(pdf_NCount > 0){
		
		 swal({
				title : '',
				text : "PDF유무가 N인것을 제외하고 병합됩니다. 위 순서로 PDF 병합을 진행 하시겠습니까?",
				type : 'info',
				showCancelButton : true,
				confirmButtonClass : "btn-danger",
				confirmButtonText : "예",
				cancelButtonText : "아니오",
				closeOnConfirm : true,
				closeOnCancel : true
			}, function(isConfirm) {
				if (isConfirm) {
					window.open( _contextPath + "/edms/interfaces/downloadPDFMerge.action?f_category_ids="+DownloadIds+"&file_nm_prefix="+file_nm_prefix);
				}else{
					
				}

			});
		
	}else{
		
		 swal({
				title : '',
				text : "위 순서로 PDF 병합을 진행 하시겠습니까?",
				type : 'info',
				showCancelButton : true,
				confirmButtonClass : "btn-danger",
				confirmButtonText : "예",
				cancelButtonText : "아니오",
				closeOnConfirm : true,
				closeOnCancel : true
			}, function(isConfirm) {
				if (isConfirm) {
					window.open( _contextPath + "/edms/interfaces/downloadPDFMerge.action?f_category_ids="+DownloadIds+"&file_nm_prefix="+file_nm_prefix);
				}else{
					
				}

			});
	}
	
	var Url = _contextPath + "/edms/ht/inWorkHistoryList.action";
    var param = "historyFormDTO.documentDTO.doc_seq_list=" + DownloadIds + "&historyFormDTO.workhistoryDTO.kind=G";
    param += "&historyFormDTO.documentDTO.ses_emp_id=" + _ses.ses_emp_id;
    param += "&historyFormDTO.documentDTO.commts=" + $('inDownloadDoc_downloadDocFormDTO_commts').value;

    new Ajax.Request(Url, {
        method: 'POST',
        parameters: param,
        asynchronous: false,
        onSuccess: function(response) {}
    });

	PopWin.hide('downloadDiv');
}


function CallBackPDFDownload(data){
	
	if (data.status.status != "S"){
		if(failDownloadFile == ''){
			
			failDownloadFile = $G('gridbox').cells(downloadId[downloadCnt-1],'doc_no').getValue();
		}else{
			failDownloadFile += ',' + $G('gridbox').cells(downloadId[downloadCnt-1],'doc_no').getValue();
		}
	}
	
	if (downloadCnt < downloadTotalCnt) {
		nextActionCheck = true;
		
		if($('inDownloadDoc_downloadDocFormDTO_file_nm_prefix').value != ''){
			var file_nm_no =  Number(downloadCnt)+1;
			file_nm_prefix = $('inDownloadDoc_downloadDocFormDTO_file_nm_prefix').value + "_" + file_nm_no;
		}
		Openfile.downloadFile(downloadId[downloadCnt], "PD",
				$('inDownloadDoc_downloadDocFormDTO_out_path').value,null,file_nm_prefix)
	} else {
		if(failDownloadFile != ''){
			alert("실패한 다운로드가 있습니다. IT 관리자에게 문의하세요. \n 실패한 파일 : "+failDownloadFile);	
		}else{
			alert("다운로드가 완료되었습니다.");
		}
		failDownloadFile = '';
		PopWin.setStatus('downloadDiv', $L('W_COMPLETE'));
		PopWin.hide('downloadDiv');
	}
}

function CallBackDownload(data) {
	
	if (data.status.status != "S"){
		if(failDownloadFile == ''){
			
			failDownloadFile = $G('gridbox').cells(downloadId[downloadCnt-1],'doc_no').getValue();
		}else{
			failDownloadFile += ',' + $G('gridbox').cells(downloadId[downloadCnt-1],'doc_no').getValue();
		}
	}
	
	if (downloadCnt < downloadTotalCnt) {
		nextActionCheck = true;
		
		if($('inDownloadDoc_downloadDocFormDTO_file_nm_prefix').value != ''){
			var file_nm_no =  Number(downloadCnt)+1;
			file_nm_prefix = $('inDownloadDoc_downloadDocFormDTO_file_nm_prefix').value + "_" + file_nm_no;
		}
		ProgressBarPlaying("PLAY", downloadCnt, downloadTotalCnt);
		Openfile.downloadFile(downloadId[downloadCnt], "D",
				$('inDownloadDoc_downloadDocFormDTO_out_path').value,null,file_nm_prefix);
	} else {
		if(failDownloadFile != ''){
			ProgressBarPlaying("ERROR", downloadCnt, downloadTotalCnt);
			alert("실패한 다운로드가 있습니다. IT 관리자에게 문의하세요. \n 실패한 파일 : "+failDownloadFile);	
		}else{
			ProgressBarPlaying("FINAL", downloadCnt, downloadTotalCnt);
			alert("다운로드가 완료되었습니다.");
		}
		failDownloadFile = '';
		PopWin.setStatus('downloadDiv', $L('W_COMPLETE'));
		PopWin.hide('downloadDiv');
	}
}

function doRequestDownload() {
	
	document.getElementById("inReqDownloadDoc").reset();
	
	var selectedId;
	var doc_seqs = "";
	if(buttonCheck == 1){
		selectedId = mygrid.getSelectedRowId();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		selectedId = $w(selectedId.gsub(',', ' '));

		if (checkLock(selectedId) == false) {
			alert($L("SN_E1000"));
			return;
		}
		//$('inReqDownloadDoc').RemoveTrAll();
		extChk = false;
		for (var i = 0; i < selectedId.size(); i++) {
			if (i != 0)
				doc_seqs += ",";
			doc_seqs += $G('gridbox').cells(selectedId[i], 'doc_seq').getValue();
			if(extChk == false && $G('gridbox').cells(selectedId[i], 'ext').getValue().toLowerCase().indexOf("dwg") != -1)
			{
				extChk = true;
			}
		}
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doRequestDownload()
		 * DataView로 인한 기능 분기
		 */
		selectedId = myDataView.getSelected();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}

		if (checkLock(selectedId) == false) {
			alert($L("SN_E1000"));
			return;
		}
		//$('inReqDownloadDoc').RemoveTrAll();
		extChk = false;
		
		try {
			// 다수 선택일 경우
			for (var i = 0; i < selectedId.size(); i++) {
				
				var data = myDataView.get(selectedId[i]);
				
				if (i != 0)
					doc_seqs += ",";
				doc_seqs += data.doc_seq;
				if(extChk == false && data.ext.toLowerCase().indexOf("dwg") != -1)
				{
					extChk = true;
				}
			}
		} catch (e) {
			// 한개 션택일 경우
			var data = myDataView.get(selectedId);
				
			doc_seqs = data.doc_seq;
			if(extChk == false && data.ext.toLowerCase().indexOf("dwg") != -1)
			{
				extChk = true;
			}
		}
		
	}

	$('inReqDownloadDoc_workFlowFormDTO_doc_seq').value = doc_seqs;

	var gridQString = _contextPath + "/edms/dc/seRDDocList.action?";
	RDgrid.clearAndLoad(gridQString + "&searchFormDTO.doc_seq_list=" + doc_seqs
			+ "&searchFormDTO.gridNames3=" + $G('ReqDownGridbox').getIds(),
			null);

	$('inReqDownloadDoc').createSequnceNum = 0;
	if(downloadWin == undefined)
	{
		create_req_window();
	}
	downloadWin.show();
	
	$('inReqDownloadDoc').AddTr('Down');
	/*if(extChk == true) //고정 결재자 지정방식
	{
		url = _contextPath + "/edms/dc/ApprovalList.action";
		new Ajax.Request(url, {
			method : 'POST',
			asynchronous : false,
			onSuccess : function(response) {
				var json = eval('(' + response.responseText + ')');
				
			    for(var i=0; i<json.size(); i++)
				{
			    	var seq = $('inReqDownloadDoc').createSequnceNum;
			    	console.log(seq);
			    	$('inReqDownloadDoc').AddAppTr('Down');
			    	$('user_nameDown'+seq).value = json[i].emp_name;
			    	$('user_nameDown'+seq).readOnly = true;
			    	$('app_userDown'+seq).value = json[i].emp_id;
			    	downloadWin.height = downloadWin.height+30;
				}
			}
		});
	}*/
	downloadWin.button("close").attachEvent("onClick", function() {
		for(var a=0; a<$('inReqDownloadDoc').createSequnceNum; a++)
		{
			 $('inReqDownloadDoc').RemoveTr();
		}
		downloadWin.hide();
		downloadWin.setModal(false);
		console.log($('inReqDownloadDoc').createSequnceNum);
	});
}
function reqDown_removeTr(removeCnt)
{
	if(removeCnt == undefined)
	var removeCnt = Number($('inReqDownloadDoc').createSequnceNum-1);
	
	if(document.getElementById('user_nameDown'+removeCnt))
	{
		if($('user_nameDown'+removeCnt).readOnly == false)
		 $('inReqDownloadDoc').RemoveTr();
	}else{
		reqDown_removeTr(Number(removeCnt-1));
	}
}
function create_req_window()
{
	downloadWin = Wins.createWindow('downloadRequestDiv', 20, 30, 420, 420);
	downloadWin.attachObject("downloadRequestDiv");
	downloadWin.setModal(true);
	downloadWin.center();
	downloadWin.setText($L('W_DOWNLOAD_REQ', '조건'));
}

function doRequestDelete() {
	
	document.getElementById("inReqDeleteDoc").reset();
	
	var selectedId;
	var doc_seqs = "";
	
	if(buttonCheck == 1){
		
		selectedId = mygrid.getSelectedRowId();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		selectedId = $w(selectedId.gsub(',', ' '));

		if (checkLock(selectedId) == false) {
			alert($L("SN_E1000"));
			return;
		}

		
		for (var i = 0; i < selectedId.size(); i++) {
			if (i != 0)
				doc_seqs += ",";
			doc_seqs += $G('gridbox').cells(selectedId[i], 'doc_seq').getValue();
		}
		
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doRequestDelete()
		 * DataView로 인한 기능 분기
		 */
		selectedId = myDataView.getSelected();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}

		if (checkLock(selectedId) == false) {
			alert($L("SN_E1000"));
			return;
		}

		try {
			// 다수 선택일 경우
			for (var i = 0; i < selectedId.size(); i++) {
				if (i != 0)
					doc_seqs += ",";
				doc_seqs += myDataView.get(selectedId[i]).doc_seq;
			}
		} catch (e) {
			// 한개 선택일 경우
			doc_seqs = myDataView.get(selectedId).doc_seq;
		}
		
	}
	
	$('inReqDeleteDoc_workFlowFormDTO_doc_seq').value = doc_seqs;

	var gridQString = _contextPath + "/edms/dc/seRDDocList.action?";
	RDELgrid.clearAndLoad(gridQString + "&searchFormDTO.doc_seq_list=" + doc_seqs
			+ "&searchFormDTO.gridNames3=" + $G('ReqDeleteGridbox').getIds(),
			null);

	$('inReqDeleteDoc').createSequnceNum = 0;
	PopWin.show('ReqDeleteDiv');
	$('inReqDeleteDoc').AddTr('Delete');
	
	PopWin.setStatus('ReqDeleteDiv', selectedId.size()
			+ $L("SN_D284", " 건이 선택되었습니다."));
	PopWin.w('ReqDeleteDiv').button("close").attachEvent("onClick",function(){
		
		for(var a=0; a<$('inReqDeleteDoc').createSequnceNum; a++)
		{
			 $('inReqDeleteDoc').RemoveTr();
		}
		
		resultRemove();
	});
	
	
}

function reqDelete() {

	if (!notNullCheck('inReqDeleteDoc_workFlowFormDTO_workflowAppDTO_remark')) {
		alert($L('SN_D091'));
		return;
	}
	
	 swal({
			title : '',
			text : $L("SN_C018"),
			type : 'info',
			showCancelButton : true,
			confirmButtonClass : "btn-danger",
			confirmButtonText : "예",
			cancelButtonText : "아니오",
			closeOnConfirm : false,
			closeOnCancel : true
		}, function(isConfirm) {
			if (isConfirm) {
				if (validateApprover($('inReqDeleteDoc').IdObs) == false) {
					alert($L('SN_SE_APP'));
					return;
				}

				var url = _contextPath + "/edms/wf/Request.action";
				var param = Form.serialize('inReqDeleteDoc');

				new Ajax.Request(url, {
					method : 'POST',
					parameters : param,
					asynchronous : false,
					onSuccess : function(response) {
						var json = eval('(' + response.responseText + ')');
						if (json.workflowDTO != undefined
								&& json.workflowDTO.work_seq != undefined) {
							alert($L('SN_I023'));
						}

					}
				});
				PopWin.setStatus('ReqDeleteDiv', $L("SN_I119", "작업이 완료되었습니다."));
				for(var a=0; a<$('inReqDeleteDoc').createSequnceNum; a++)
				{
					 $('inReqDeleteDoc').RemoveTr();
				}
				PopWin.hide('ReqDeleteDiv');
			}else{
				return;
			}

		});
	
}

/*
 * 2014.05.30 WooChul Jung event function when user pressed download button in
 * downloadRequestDiv popup window.
 * 
 */
function reqDownload() {

	if (!notNullCheck('inReqDownloadDoc_workFlowFormDTO_workflowAppDTO_remark')) {
		return;
	}
	
	 swal({
			title : '',
			text : $L("SN_C018"),
			type : 'info',
			showCancelButton : true,
			confirmButtonClass : "btn-danger",
			confirmButtonText : "예",
			cancelButtonText : "아니오",
			closeOnConfirm : false,
			closeOnCancel : true
		}, function(isConfirm) {
			if (isConfirm) {
				if (validateApprover($('inReqDownloadDoc').IdObs) == false) {
					alert($L('SN_SE_APP'));
					return;
				}

				var url = _contextPath + "/edms/wf/Request.action";
				var param = Form.serialize('inReqDownloadDoc');

				/*
				 * 2015.06.22 WooChul Jung. TIPS use Groupware for enrollment approval.
				 */
				// param = param + "&workFlowFormDTO.appList.app_user=" + _ses.empDTO.emp_id
				// ;
				new Ajax.Request(url, {
					method : 'POST',
					parameters : param,
					asynchronous : false,
					onSuccess : function(response) {
						var json = eval('(' + response.responseText + ')');
						if (json.workflowDTO != undefined
								&& json.workflowDTO.work_seq != undefined) {
							alert($L('SN_I023'));
						}
					}
				});
				//PopWin.setStatus('downloadRequestDiv', $L("SN_I119", "작업이 완료되었습니다."));
				for(var a=0; a<$('inReqDownloadDoc').createSequnceNum; a++)
				{
					 $('inReqDownloadDoc').RemoveTr();
				}
				downloadWin.hide('downloadRequestDiv');
				downloadWin.setModal(false);
			}else{
				return;
			}

		});
	
}

function checkLock(selectedId) {

	if(buttonCheck == 1){
		
		for (var i = 0; i < selectedId.size(); i++) {
			var lock_status = $G('gridbox').cells(selectedId[i], 'lock_status')
					.getValue();
			if (lock_status == 'T')
				return false;
		}
		
	}else{
		/**
		 * 2020.12.08(화)
		 * function : checkLock(selectedId)
		 * DataView로 인한 기능 분기
		 */
		try {
			// 다수 선택일 경우
			for (var i = 0; i < selectedId.size(); i++) {
				var lock_status = myDataView.get(selectedId[i]).lock_status;
				if (lock_status == 'T')
					return false;
			}
		} catch (e) {
			// 한개 선택일 경우
			var lock_status = myDataView.get(selectedId).lock_status;
			if (lock_status == 'T')
				return false;
		}
		
		
	}
	
	return true;
}

function doDownloadIndex(type) {
	var selectedId;
	if(buttonCheck == 1){
		
		selectedId = mygrid.getSelectedRowId();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doDownloadIndex()
		 * DataView로 인한 기능 분기
		 */
		selectedId = myDataView.getSelected();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		
		try {
			// 다수 선택일 경우
			var t = selectedId.size();
			selectedId= selectedId.join();
		} catch (e) {
			// 한개 선택일 경우
			selectedId= selectedId;
		}
		
		
	}

	var param = "doc_seqs=" + selectedId;
	url = _contextPath + "/edms/dc/seDocExcel.action";
	fnCreateIframe("excel");
	var win = window.open("","excel","_blank");
	win.document.write("<html><body>");
	win.document.write('<form id="myform" method="post" action="'
					+ url
					+ '" accept-charset="utf-8" enctype="text/html"><input type="hidden" name="doc_seqs" id="doc_seqs"/><input type="hidden" name="excel_form" id="excel_form"/> </form>');
	win.document.getElementById("doc_seqs").value = selectedId;
	win.document.getElementById("excel_form").value = type;
	win.document.write("</body></html>");
	win.document.getElementById("myform").submit();
	win.document.close();
}

// by HDO
function doDownloadAllIndex(type) {
	var param = null;
	$('Search_desc').style.display = "none";
	if (!$G('gridbox').isColumnHidden('result')) {
		$G('gridbox').setColumnHidden('result', true);
	}
	if ($('select_searchFormDTO_documentDTO_i_user_name').value == "") {
		$('select_searchFormDTO_documentDTO_i_user_id').value = "";
	}
	if ($('select_searchFormDTO_documentDTO_u_user_name').value == "") {
		$('select_searchFormDTO_documentDTO_u_user_id').value = "";
	}

	param = ClearParameter(Form.serialize('select', true));

	gridQString = _contextPath + "/edms/dc/seDocExcelInKindTree.action?"+param;
	fnCreateIframe("AllExcel");
	var win = window.open("","AllExcel","_blank");
	win.document.write("<html><body>");
	win.document.write('<form id="ExcelForm" method="post" action="' + gridQString
			+ '" accept-charset="utf-8" enctype="text/html"><input type="hidden" name="excel_form" id="excel_form"/></form>');
	win.document.write("</body></html>");
	win.document.getElementById("excel_form").value = type;
	win.document.getElementById("ExcelForm").submit();
	win.document.close();
}
// by HDO

/*
 * 2014.06.16 WooChul Jung Add two function 1. download function permits to
 * download only one document. 2. There is a download authority like printing
 * and viewing, so needs to check authority.
 */
function doDownload() {
	file_nm_prefix = "";
	$('inDownloadDoc_downloadDocFormDTO_file_nm_prefix').value = '';
	downloadStatus = "D";
	var selectedId;
	
	if(buttonCheck == 1){
		
		selectedId = mygrid.getSelectedRowId();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		selectedId = $w(selectedId.gsub(',', ' '));
		
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doDownload()
		 * DataView로 인한 기능 분기
		 */
		
		selectedId = myDataView.getSelected();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		
	}
	
	/*
	 * 2014.06.02 WooChul Jung User can download only one file. So if user
	 * select more than one file, show alarm message.
	 */
	try {
		// 다수 선택일 경우
		if (selectedId.size() > 1) {
			alert($L("SN_E0001"));
			return;
		}
	} catch (e) {
		// 한개 선태길 경우
		selectedId = selectedId;
	}
	document.getElementById("inDownloadDoc").reset();
	
	//add_code 20211221 다운로드 저장경로 조회
	let savePath = seMyPreference('DP');
	//end
	
	if(downloadPathCheck == "Y" && savePath != undefined)
	{
		 $('inDownloadDoc_downloadDocFormDTO_out_path').value = savePath;
	}
	 swal({
			title : '보안경고',
			text : _securityAlertMessage,
			type : 'warning',
			showCancelButton : true,
			confirmButtonClass : "btn-danger",
			confirmButtonText : "동의",
			cancelButtonText : "동의안함",
			customClass : 'swal_security_waring',
			closeOnConfirm : true,
			closeOnCancel : false
		}, function(isConfirm) {
			if (isConfirm) {
				downloadStatus = "D";
				var selectedId;
				failDownloadFile = '';
				file_nm_prefix = "";
				$('inDownloadDoc_downloadDocFormDTO_file_nm_prefix').value = '';
				
				if(buttonCheck == 1){
					
					selectedId = mygrid.getSelectedRowId();
					if (selectedId == null) {
						this.closeOnConfirm = false;
						alert($L('SN_D233'));
						return;
					}
					if (selectedId != null) {
						selectedId = $w(selectedId.gsub(',', ' '));
					}
					
				}else{
					/**
					 * 2020.12.08(화)
					 * function : doMultiDownload()
					 * DataView로 인한 기능 분기
					 */
					selectedId = myDataView.getSelected();
					if (selectedId == null) {
						this.closeOnConfirm = false;
						alert($L('SN_D233'));
						return;
					}
					
					
				}
				
				var downgridid = DownloadPopUpgrid.getAllRowIds();
				downgridid = $w(downgridid.gsub(',', ' '));
				downgridid.each(function(rId) {
					if (rId.startsWith('_N')) {
						DownloadPopUpgrid.deleteRow(rId);
						return;
					} else {
						DownloadPopUpgrid.deleteRow(rId);
						return;
					}
				});
				
				if(buttonCheck == 1){
					for (var i = 0; i < selectedId.size(); i++) {
						var row = $G('DownloadPopUpgridbox').defValue;
						var newID = $G('gridbox').cells(selectedId[i], 'doc_seq').getValue();
						row.set('ext', $G('gridbox').cells(selectedId[i], 'ext').getValue());
						row.set('doc_no', $G('gridbox').cells(selectedId[i], 'doc_no').getValue());
						row.set('doc_name', $G('gridbox').cells(selectedId[i], 'doc_name').getValue());
						row.set('rev_no', $G('gridbox').cells(selectedId[i], 'rev_no').getValue());
						row.set('pdf_yn', $G('gridbox').cells(selectedId[i], 'pdf_yn').getValue());
						$G('DownloadPopUpgridbox').addRow(newID, row);
						$G('DownloadPopUpgridbox').cells(newID, 'ext').setValue($G('gridbox').cells(selectedId[i], 'ext').getValue());
					}
				}else{
					try {
						for (var i = 0; i < selectedId.size(); i++) {
							var row = $G('DownloadPopUpgridbox').defValue;
							var newID = myDataView.get(selectedId[i]).doc_seq;
							row.set('ext', myDataView.get(selectedId[i]).ext);
							row.set('doc_no', myDataView.get(selectedId[i]).doc_no);
							row.set('doc_name', myDataView.get(selectedId[i]).doc_name);
							row.set('rev_no',myDataView.get(selectedId[i]).rev_no);
							row.set('pdf_yn',myDataView.get(selectedId[i]).pdf_yn);
							$G('DownloadPopUpgridbox').addRow(newID, row);
//							$G('DownloadPopUpgridbox').cells(newID, 'ext').setValue($G('gridbox').cells(selectedId[i], 'ext').getValue());
						}
					}catch (e) {
						var row = $G('DownloadPopUpgridbox').defValue;
						var newID = myDataView.get(selectedId).doc_seq;
						row.set('ext', myDataView.get(selectedId).ext);
						row.set('doc_no', myDataView.get(selectedId).doc_no);
						row.set('doc_name', myDataView.get(selectedId).doc_name);
						row.set('rev_no',myDataView.get(selectedId).rev_no);
						row.set('pdf_yn',myDataView.get(selectedId).pdf_yn);
						$G('DownloadPopUpgridbox').addRow(newID, row);
//							$G('DownloadPopUpgridbox').cells(newID, 'ext').setValue($G('gridbox').cells(selectedId[i], 'ext').getValue());
					}
					
				}
				
				
				var url = _contextPath + "/edms/dc/seRoleCheck.action";
				var emp_id = _ses.empDTO.emp_id;
				var param = "emp_id=" + emp_id;

				var cnt = 0;
				var extFlag = false;
				new Ajax.Request(url, {
					method : 'POST',
					parameters : param + "&doc_seq=" + selectedId,
					asynchronous : true,
					onSuccess : function(response) {
						var json = eval('(' + response.responseText + ')');
						cnt = json;
						// add20161207 권한체크
						if (Number(cnt) != 0) {
							if (Number(cnt) == 0 && selectedId != null) {
								for (var i = 0; i < selectedId.size(); i++) {
									
									if(buttonCheck == 1){
										
										var tId = $G('gridbox').cells(selectedId[i], 'tree_id').getValue();
										if (tId == "T587" || tId == "T588") { // 개발 // if(tId
											var type = $G('gridbox').cells(selectedId[i], 'ext').getValue();
											if (type == 'dwgF') {
												extFlag = true;
											}
										}
										
									}else{
										
										var data = myDataView.get(selectedId[i]); 
										
										var tId = data.tree_id;
										if (tId == "T587" || tId == "T588") { // 개발 // if(tId
											var type = data.ext;
											if (type == 'dwgF') {
												extFlag = true;
											}
										}
										
									}
								}
							}

							if (extFlag) {
								this.closeOnConfirm = false;
								alert($L('SN_DOWN_CHK'));
								return;

							}

							// end

							// --------- End --------------
							setCommtsList();
							
							PopWin.show('downloadDiv');
							PopWin.setStatus('downloadDiv', selectedId.size()
									+ $L("SN_D284", "건이 선택되었습니다."));
							PopWin.w('downloadDiv').button("close").attachEvent("onClick",
									resultRemove);

						} else {
							this.closeOnConfirm = false;
							alert($L('SN_E0004'));
						}
						// end
					}
				});
			}else{
				alert('동의하지 않으시면 다운로드를 하실 수 없습니다.');
			}
			
		}
	);
}

function doMultiDownload() {
	document.getElementById("inDownloadDoc").reset();
	//add_code 20211221 다운로드 저장경로 조회
	let savePath = seMyPreference('DP');
	//end
	if(downloadPathCheck == "Y" && savePath != undefined)
	{
		 $('inDownloadDoc_downloadDocFormDTO_out_path').value = savePath;
	}
	 swal({
			title : '보안경고',
			text : _securityAlertMessage,
			type : 'warning',
			showCancelButton : true,
			confirmButtonClass : "btn-danger",
			confirmButtonText : "동의",
			cancelButtonText : "동의안함",
			customClass : 'swal_security_waring',
			closeOnConfirm : true,
			closeOnCancel : false
		}, function(isConfirm) {
			if (isConfirm) {
				downloadStatus = "D";
				var selectedId;
				failDownloadFile = '';
				file_nm_prefix = "";
				$('inDownloadDoc_downloadDocFormDTO_file_nm_prefix').value = '';
				
				if(buttonCheck == 1){
					
					selectedId = mygrid.getSelectedRowId();
					if (selectedId == null) {
						this.closeOnConfirm = false;
						alert($L('SN_D233'));
						return;
					}
					if (selectedId != null) {
						selectedId = $w(selectedId.gsub(',', ' '));
					}
					
				}else{
					/**
					 * 2020.12.08(화)
					 * function : doMultiDownload()
					 * DataView로 인한 기능 분기
					 */
					selectedId = myDataView.getSelected();
					if (selectedId == null) {
						this.closeOnConfirm = false;
						alert($L('SN_D233'));
						return;
					}
					
					
				}
				
				var downgridid = DownloadPopUpgrid.getAllRowIds();
				downgridid = $w(downgridid.gsub(',', ' '));
				downgridid.each(function(rId) {
					if (rId.startsWith('_N')) {
						DownloadPopUpgrid.deleteRow(rId);
						return;
					} else {
						DownloadPopUpgrid.deleteRow(rId);
						return;
					}
				});
				
				if(buttonCheck == 1){
					for (var i = 0; i < selectedId.size(); i++) {
						var row = $G('DownloadPopUpgridbox').defValue;
						var newID = $G('gridbox').cells(selectedId[i], 'doc_seq').getValue();
						row.set('ext', $G('gridbox').cells(selectedId[i], 'ext').getValue());
						row.set('doc_no', $G('gridbox').cells(selectedId[i], 'doc_no').getValue());
						row.set('doc_name', $G('gridbox').cells(selectedId[i], 'doc_name').getValue());
						row.set('rev_no', $G('gridbox').cells(selectedId[i], 'rev_no').getValue());
						row.set('pdf_yn', $G('gridbox').cells(selectedId[i], 'pdf_yn').getValue());
						$G('DownloadPopUpgridbox').addRow(newID, row);
						$G('DownloadPopUpgridbox').cells(newID, 'ext').setValue($G('gridbox').cells(selectedId[i], 'ext').getValue());
					}
				}else{
					try {
						for (var i = 0; i < selectedId.size(); i++) {
							var row = $G('DownloadPopUpgridbox').defValue;
							var newID = myDataView.get(selectedId[i]).doc_seq;
							row.set('ext', myDataView.get(selectedId[i]).ext);
							row.set('doc_no', myDataView.get(selectedId[i]).doc_no);
							row.set('doc_name', myDataView.get(selectedId[i]).doc_name);
							row.set('rev_no',myDataView.get(selectedId[i]).rev_no);
							row.set('pdf_yn',myDataView.get(selectedId[i]).pdf_yn);
							$G('DownloadPopUpgridbox').addRow(newID, row);
//							$G('DownloadPopUpgridbox').cells(newID, 'ext').setValue($G('gridbox').cells(selectedId[i], 'ext').getValue());
						}
					}catch (e) {
						var row = $G('DownloadPopUpgridbox').defValue;
						var newID = myDataView.get(selectedId).doc_seq;
						row.set('ext', myDataView.get(selectedId).ext);
						row.set('doc_no', myDataView.get(selectedId).doc_no);
						row.set('doc_name', myDataView.get(selectedId).doc_name);
						row.set('rev_no',myDataView.get(selectedId).rev_no);
						row.set('pdf_yn',myDataView.get(selectedId).pdf_yn);
						$G('DownloadPopUpgridbox').addRow(newID, row);
//							$G('DownloadPopUpgridbox').cells(newID, 'ext').setValue($G('gridbox').cells(selectedId[i], 'ext').getValue());
					}
					
				}
				
				
				var url = _contextPath + "/edms/dc/seRoleCheck.action";
				var emp_id = _ses.empDTO.emp_id;
				var param = "emp_id=" + emp_id;

				var cnt = 0;
				var extFlag = false;
				new Ajax.Request(url, {
					method : 'POST',
					parameters : param + "&doc_seq=" + selectedId,
					asynchronous : true,
					onSuccess : function(response) {
						var json = eval('(' + response.responseText + ')');
						cnt = json;
						// add20161207 권한체크
						if (Number(cnt) != 0) {
							if (Number(cnt) == 0 && selectedId != null) {
								for (var i = 0; i < selectedId.size(); i++) {
									
									if(buttonCheck == 1){
										
										var tId = $G('gridbox').cells(selectedId[i], 'tree_id').getValue();
										if (tId == "T587" || tId == "T588") { // 개발 // if(tId
											var type = $G('gridbox').cells(selectedId[i], 'ext').getValue();
											if (type == 'dwgF') {
												extFlag = true;
											}
										}
										
									}else{
										
										var data = myDataView.get(selectedId[i]); 
										
										var tId = data.tree_id;
										if (tId == "T587" || tId == "T588") { // 개발 // if(tId
											var type = data.ext;
											if (type == 'dwgF') {
												extFlag = true;
											}
										}
										
									}
								}
							}

							if (extFlag) {
								this.closeOnConfirm = false;
								alert($L('SN_DOWN_CHK'));
								return;

							}

							// end

							// --------- End --------------
							setCommtsList();
							
							PopWin.show('downloadDiv');
							PopWin.setStatus('downloadDiv', selectedId.size()
									+ $L("SN_D284", "건이 선택되었습니다."));
							PopWin.w('downloadDiv').button("close").attachEvent("onClick",
									resultRemove);

						} else {
							this.closeOnConfirm = false;
							alert($L('SN_E0004'));
						}
						// end
					}
				});
			}else{
				alert('동의하지 않으시면 다운로드를 하실 수 없습니다.');
			}
			
		}
	);
}

/*
 * 2014.06.12 WooChul Jung Add Equip enrollment in P&ID CAD
 */
function doCadEquipInfo() {
	
	var rowIds;
	
	if(buttonCheck == 1){
		rowIds = mygrid.getSelectedRowId();
		
		if (rowIds.size() == 0) {
			alert($L("SN_E0003"));
			return;
		}
		rowIds = $w(rowIds.gsub(',', ' '));
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doMultiDownload()
		 * DataView로 인한 기능 분기
		 */
		rowIds = myDataView.getSelected();
		
		if (rowIds.size() == 0) {
			alert($L("SN_E0003"));
			return;
		}
	}
	
	try {
		PopWin.show('CADEnrollInfoDiv');
		PopWin.setText('CADEnrollInfoDiv', "[" + rowIds.size() + "]");
	} catch (e) {
		selectedId = rowIds;
		// check whether this CAD is already applied with Equip rule
		alreadyRegistered = isAlreadyEnrolled(selectedId);

		if (alreadyRegistered != null && alreadyRegistered.startsWith("OK")) {
			doCadEquipInfoRequest();
		} else {
			PopWin.show('CADEnrollInfoDiv');
			PopWin.setText('CADEnrollInfoDiv', "[" + rowIds.size() + "]");
		}
	}
}

function doCadEquipInfoRequest() {
	var undef;
	var rowIds;
	
	if(buttonCheck == 1){
		
		rowIds = mygrid.getSelectedRowId();

		PopWin.hide('CADEnrollInfoDiv');

		rowIds = $w(rowIds.gsub(',', ' '));
		
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doMultiDownload()
		 * DataView로 인한 기능 분기
		 */
		rowIds = myDataView.getSelected();

		PopWin.hide('CADEnrollInfoDiv');

	}
	
	try {
		// 다수 선택일 경우
		for (var i = 0; i < rowIds.size(); i++) {
			selectedId = rowIds[i];
			var ext = buttonCheck == 1?$G('gridbox').cells(selectedId, 'ext').getValue() : myDataView.get(selectedId).ext;
			
			ext = ext.substring(0, ext.length - 1);

			if (ext != null)
				ext = ext.toUpperCase();

			if ((ext != 'DWG') && (ext != 'DXF')) {
				// alert($L("SN_E0002"));
			} else {
				
				var doc_seq = buttonCheck == 1? $G('gridbox').cells(selectedId, 'doc_seq').getValue() : myDataView.get(selectedId).doc_seq;
				var doc_name = buttonCheck == 1? $G('gridbox').cells(selectedId, 'doc_name').getValue() : myDataView.get(selectedId).doc_name;
				
				if (rowIds.size() != (i + 1)) {
					var ___preview = docviewUserEnroll(doc_seq, undef, undef, 'Y',
							$('CADUserEnroll_CADUserEnroll_ruleNo').value,
							$('CADUserEnroll_CADUserEnroll_UnitNO').value,
							$('CADUserEnroll_CADUserEnroll_excludeLayer').value,
							true);

					___preview.hide();
				} else {
					var ___preview = docviewUserEnroll(doc_seq, undef, undef, 'Y',
							$('CADUserEnroll_CADUserEnroll_ruleNo').value,
							$('CADUserEnroll_CADUserEnroll_UnitNO').value,
							$('CADUserEnroll_CADUserEnroll_excludeLayer').value);
					if (___preview != null) {
						if (selectedId != null)
							___preview.setText(doc_name);
					}
				}
			}
		}
		
	} catch (e) {
		// 한개 선택일 경우
		selectedId = rowIds;
		var ext = buttonCheck == 1?$G('gridbox').cells(selectedId, 'ext').getValue() : myDataView.get(selectedId).ext;
		
		ext = ext.substring(0, ext.length - 1);

		if (ext != null)
			ext = ext.toUpperCase();

		if ((ext != 'DWG') && (ext != 'DXF')) {
			// alert($L("SN_E0002"));
		} else {
			
			var doc_seq = buttonCheck == 1? $G('gridbox').cells(selectedId, 'doc_seq').getValue() : myDataView.get(selectedId).doc_seq;
			var doc_name = buttonCheck == 1? $G('gridbox').cells(selectedId, 'doc_name').getValue() : myDataView.get(selectedId).doc_name;
			
			var ___preview = docviewUserEnroll(doc_seq, undef, undef, 'Y',
					$('CADUserEnroll_CADUserEnroll_ruleNo').value,
					$('CADUserEnroll_CADUserEnroll_UnitNO').value,
					$('CADUserEnroll_CADUserEnroll_excludeLayer').value);
			if (___preview != null) {
				if (selectedId != null)
					___preview.setText(doc_name);
			}
		}
	}

}

// 일반 수정요청
function doRequestChange() {
	
	document.getElementById("Request").reset();
	changeStatus = "";
	if(buttonCheck == 1){
		
		downloadId = mygrid.getSelectedRowId();
		if (downloadId == null) {
			alert($L('SN_D233'));
			return;
		}
		
		var checkLockIds = $w(downloadId.gsub(',', ' '));
		
		if(!checkLock(checkLockIds)){
			alert($L("SN_E1000"));
			return;
		}
		
		$('Request_workFlowFormDTO_doc_seq').value = downloadId; // 2014.2.12 수정
		// doc_seq에 파일을 넘겨주는 부분
		
		var url = _contextPath + "/edms/dc/seCheckOutDoc.action";
		var param = "checkOutDTO.doc_seq_list="+downloadId;
		new Ajax.Request(url,
				{
					method : 'POST',
					parameters : param,
					asynchronous : false,
					onSuccess : function(response) {
						if(response.responseText == "error"){
							alert("1.최신 도면이 아닌 문서가 포함되어 있습니다.\n2.삭제함에 최신문서가 존재합니다. 도면관리자에게 문의바랍니다.");
							return false;
						}else{
							var gridQString = _contextPath + "/edms/dc/seRCDocList.action?";
							var param = "searchFormDTO.doc_seq_list=" + downloadId;
							RCgrid.clearAndLoad(gridQString + param + "&searchFormDTO.gridNames="
									+ $G('ReqChangegridbox').getIds(), function() {
							});
							
							downloadId = $w(downloadId.gsub(',', ' '));
							
							downloadTotalCnt = downloadId.size();
							
							downloadCnt = 0;
							
							
							markupFiles.length = 0;
							attachFiles.length = 0;
							$('Request').createSequnceNum = 0;
							PopWin.show('ReqChangeDiv');
							$('Request').AddTr('Change');
							PopWin.w('ReqChangeDiv').button("close").attachEvent("onClick", function() {
								
								for(var a=0; a<$('Request').createSequnceNum; a++)
								{
									 $('Request').RemoveTr();
								}
								
								PopWin.hide('ReqChangeDiv')
								PopWin.w('ReqChangeDiv').setModal(false);
								console.log($('inReqDownloadDoc').createSequnceNum);
								// if (preViewDiv)
								// preViewDiv.hide();
							});
						}
					}
				});
		
		
		
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doRequestChange()
		 * DataView로 인한 기능 분기
		 */
		downloadId = myDataView.getSelected();
		if (downloadId == null) {
			alert($L('SN_D233'));
			return;
		}
		
		if(!checkLock(downloadId)){
			alert($L("SN_E1000"));
			return;
		}
		
		try{
			// 다수 선택일 경우
			var t = downloadId.size();			// 다수인지 판단
			
			downloadId = downloadId.join(',');
			
			$('Request_workFlowFormDTO_doc_seq').value = downloadId; // 2014.2.12 수정
			// doc_seq에 파일을 넘겨주는 부분
			
			var url = _contextPath + "/edms/dc/seCheckOutDoc.action";
			var param = "checkOutDTO.doc_seq_list="+downloadId;
			new Ajax.Request(url,
					{
						method : 'POST',
						parameters : param,
						asynchronous : false,
						onSuccess : function(response) {
							if(response.responseText == "error"){
								alert("1.최신 도면이 아닌 문서가 포함되어 있습니다.\n2.삭제함에 최신문서가 존재합니다. 도면관리자에게 문의바랍니다.");
								return false;
							}else{
								var gridQString = _contextPath + "/edms/dc/seRCDocList.action?";
								var param = "searchFormDTO.doc_seq_list=" + downloadId;
								RCgrid.clearAndLoad(gridQString + param + "&searchFormDTO.gridNames="
										+ $G('ReqChangegridbox').getIds(), function() {
								});
								
								downloadId = $w(downloadId.gsub(',', ' '));
								
								downloadTotalCnt = downloadId.size();
								
								downloadCnt = 0;
								
								
								markupFiles.length = 0;
								attachFiles.length = 0;
								
								
								$('Request').createSequnceNum = 0;
								PopWin.show('ReqChangeDiv');
								$('Request').AddTr('Change');
								PopWin.w('ReqChangeDiv').button("close").attachEvent("onClick", function() {
									
									for(var a=0; a<$('Request').createSequnceNum; a++)
									{
										 $('Request').RemoveTr();
									}
									
									PopWin.hide('ReqChangeDiv')
									PopWin.w('ReqChangeDiv').setModal(false);
									console.log($('inReqDownloadDoc').createSequnceNum);
									// if (preViewDiv)
									// preViewDiv.hide();
								});
							}
						}
					});
			
			
		}catch (e) {
			// 한개 선택일 경우
			downloadId = downloadId;
			$('Request_workFlowFormDTO_doc_seq').value = downloadId; // 2014.2.12 수정
			// doc_seq에 파일을 넘겨주는 부분
			
			var url = _contextPath + "/edms/dc/seCheckOutDoc.action";
			var param = "checkOutDTO.doc_seq_list="+downloadId;
			new Ajax.Request(url,
					{
						method : 'POST',
						parameters : param,
						asynchronous : false,
						onSuccess : function(response) {
							if(response.responseText == "error"){
								alert("1.최신 도면이 아닌 문서가 포함되어 있습니다.\n2.삭제함에 최신문서가 존재합니다. 도면관리자에게 문의바랍니다.");
								return false;
								
							}else{
								var gridQString = _contextPath + "/edms/dc/seRCDocList.action?";
								var param = "searchFormDTO.doc_seq_list=" + downloadId;
								RCgrid.clearAndLoad(gridQString + param + "&searchFormDTO.gridNames="
										+ $G('ReqChangegridbox').getIds(), function() {
								});
								
								downloadTotalCnt = 1;
								
								downloadCnt = 0;
								
								
								markupFiles.length = 0;
								attachFiles.length = 0;
								
								
								$('Request').createSequnceNum = 0;
								PopWin.show('ReqChangeDiv');
								$('Request').AddTr('Change');
								PopWin.w('ReqChangeDiv').button("close").attachEvent("onClick", function() {
									
									for(var a=0; a<$('Request').createSequnceNum; a++)
									{
										 $('Request').RemoveTr();
									}
									
									PopWin.hide('ReqChangeDiv')
									PopWin.w('ReqChangeDiv').setModal(false);
									console.log($('inReqDownloadDoc').createSequnceNum);
									// if (preViewDiv)
									// preViewDiv.hide();
								});
								
							}
						}
					});
			
			
			
		}
		
		
	}
	
	
}

var _resultRow = new Array();
function resultRemove() {
	for (var i = 0; i < _resultRow.length; i++) {
		$G('gridbox').cells(_resultRow[i], 'result').setValue("");
	}
	$G('gridbox').setColumnHidden('result', true);
}
function markup() {
	
	var selectedId = RCgrid.getSelectedRowId();
	
	
	if (selectedId == null) {
		alert($L('SN_D233'));
		return;
	}
	
	selectedId = $w(selectedId.gsub(',', ' '));
	
	if(selectedId.size() > 1)
	{
		alert("하나의 자료를 선택하여 주시기 바랍니다.");
		return;
	}
	
	var ext = buttonCheck == 1? $G('gridbox').cells(selectedId[0], 'ext').getValue() : myDataView.get(selectedId[0]).ext;
	if (ext.substring(0,Number(ext.length-1)).toLowerCase() != "dwg" &&
			ext.substring(0,Number(ext.length-1)).toLowerCase() != "tif") {
		alert($L('SN_NOT_SUPPORT_EXTENSION'));
		return;
	}
	
	var isExist = (markupFiles.indexOf(selectedId[0])!== -1);
	if(isExist == false)
	{
		markupFiles.push(selectedId[0]);
	}
	
	var param = "?socketDTO.f_category_id="
			+ escape($G('ReqChangegridbox').cells(selectedId,
					'doc_seq').getValue());
	var url = _contextPath + "/edms/socket/seDocumentOpen.action";
	new Ajax.Request(url, {
		method : 'POST',
		parameters : param.toQueryParams(),
		asynchronous : false,
		onSuccess : function(response) {
			DocumentOpen(response.responseText);
		}
	});
}

function ReqChangegridboxonRowDblClicked(rId,cInd){
	
	var param = "?socketDTO.f_category_id="
		+ escape($G('ReqChangegridbox').cells(rId,'doc_seq').getValue());
	var url = _contextPath + "/edms/socket/seDocumentOpen.action";
	new Ajax.Request(url, {
		method : 'POST',
		parameters : param.toQueryParams(),
		asynchronous : false,
		onSuccess : function(response) {
			DocumentOpen(response.responseText);
		}
	});
	
}

function reqChange() {
	if ($('Request_workFlowFormDTO_workflowDTO_emp_id').value == "") {
		$('Request_workFlowFormDTO_workflowDTO_emp_id').focus();
		alert($L('SN_SE_WORKER'));
		return;
	}
	
	 swal({
			title : '',
			text : $L("SN_C018"),
			type : 'info',
			showCancelButton : true,
			confirmButtonClass : "btn-danger",
			confirmButtonText : "예",
			cancelButtonText : "아니오",
			closeOnConfirm : false,
			closeOnCancel : true
		}, function(isConfirm) {
			if (isConfirm) {
				if (validateApprover($('Request').IdObs) == false) {
					alert($L('SN_SE_APP'));
					return;
				}
				if (return_fid != "") {
					$('Request_workFlowFormDTO_workflowAppDTO_markup_list').value = return_fid
							.substring(0, return_fid.lastIndexOf(","));
				}
				var url = _contextPath + "/edms/wf/Request.action";
				var param = Form.serialize('Request');

				new Ajax.Request(url, {
					method : 'POST',
					parameters : param,
					asynchronous : false,
					onSuccess : function(response) {
						var json = eval('(' + response.responseText + ')');
						if (json.workflowDTO != undefined
								&& json.workflowDTO.work_seq != undefined) {
							attach_seq = json.workflowDTO.work_seq;
							markup_wf_seq = json.workflowDTO.work_seq;
							// preViewDiv.hide();
						}
					}
				});
				return_fid = "";
				markupFileUpload(markup_wf_seq);
				attachFileUpload();
				
				for(var a=0; a<$('Request').createSequnceNum; a++)
				{
					 $('Request').RemoveTr();
				}
				
				alert($L('SN_I023'));
				PopWin.setStatus('ReqChangeDiv', $L("SN_I119", "작업이 완료되었습니다."));
				PopWin.hide('ReqChangeDiv');
				
				
				
			}else{
				return;
			}

		});
}


function markupFileUpload(work_seq){
	/**
	 * 각 파일 직접 업로드(데몬X)
	 */
	var markupIds = RCgrid.getAllRowIds();
	markupIds = $w(markupIds.gsub(',', ' '));
	
	for(var i = 0; i < markupIds.size(); i++){
		changeStatus = "MARKUP";
		rId = markupIds[i];
		if(document.getElementById("markup_"+rId) != null){
			
			if(document.getElementById("markup_"+rId).value != ''){
				var markupseq = document.getElementById("markup_"+rId).value;
				var markup_doc_seq = $G("ReqChangegridbox").cells(rId, "doc_seq").getValue(); 
				
				var markup_param = "doc_seq="+markup_doc_seq+"&markupseq="+markupseq+"&work_seq="+work_seq;
				var markupInWfUrl = _contextPath + "/edms/wf/markupWfIn.action?";
				new Ajax.Request(markupInWfUrl, {
					method : 'POST',
					parameters : markup_param,
					asynchronous : false,
					onSuccess : function(response) {
						
					}
				});
			}
			
			
		}
		
	}
}

function attachFileUpload() {
	
	/**
	 * 각 파일 직접 업로드(데몬X)
	 */
	var attachIds = RCgrid.getAllRowIds();
	attachIds = $w(attachIds.gsub(',', ' '));
	
	for(var i = 0; i < attachIds.size(); i++){
		changeStatus = "ATTACH";
		rId = attachIds[i];
		var attach_file = document.getElementById("attach_file_"+rId).files;
		var attach_doc_seq = $G("ReqChangegridbox").cells(rId, "doc_seq").getValue(); 
		
		if(attach_file.length > 0){
			Openfile.addAttachFileByFileName(attach_seq, attach_file[0].name, attach_doc_seq,attach_file[0]);
		}
	}
	
}
function CallBackSaveRows(data, requestData) {
	if (changeStatus == "ATTACH") {
		attachCnt++;
		attachFileUpload();
	} else {
		moveNextRow(data, requestData);
	}
}

/*
 * 2014.06.11 WooChul Jung add favorite Init function
 */
function favoriteInit() {
	var init = {
		parent : 'favoriteTreeBox',
		image_path : _contextPathURL + "/dhtmlxSuite/skins/web/imgs/dhxtree_web/",
		firstURL : _contextPath
				+ '/edms/tr/seGetRoleLazeLoadFavorite.action?id=0',
		width : "100%",
		height : "100%",
		insertItem : {
			parentid : 'pid',
			id : 'tid',
			name : 'tname'
		}
	};
	favoriteTree = makeAttachTree(tab.cells("d"), init);
	favoriteTree.enableAutoTooltips(true);
}

/*
 * 2014.06.11 WooChul Jung add callback function for favorite (The syntax of
 * function follows like #init-parent item# + 'onSelect')
 */
function favoriteTreeBoxonSelect(id) {

	favoriteSelId = id;
	var searchs = id.split(':::');

	if (searchs.length == 1) {
		gridQString = _contextPath + "/edms/dc/seFavoriteDocSearch.action?";
		var param = "searchFormDTO.documentDTO.dockind_id=-1&searchFormDTO.documentDTO.tree_id=-1";

		mygrid.enablePaging(true,seSearchCount("SC"),10,"page",true,"infoArea");
		mygrid.setPagingSkin("bricks")

		mygrid.clearAndLoad(gridQString + param + "&searchFormDTO.gridNames="
				+ $G('gridbox').getIds(), function() {
			waiter.hide();
		});
	} else {
		if (searchs.length != 2)
			return;

		var kind_id = searchs[0];
		var tree_id = searchs[1];

		$("select_searchFormDTO_documentDTO_dockind_id").value = kind_id;
		$("select_searchFormDTO_documentDTO_tree_id").value = tree_id;

		var url = _contextPath + "/edms/tr/seGetChildTreeId.action";
		var param = "treeDTO.t_category=T&treeDTO.tid=" + tree_id;
		new Ajax.Request(url, {
			method : 'POST',
			parameters : param,
			asynchronous : false,
			onSuccess : function(response) {
				var json = eval("(" + response.responseText + ")");
				for (var i = 0; i < json.length; i++) {
					savedTreeIdArray.push(json[i].tid);
				}
			}
		});
		var param = "treeDTO.t_category=K&treeDTO.tid=" + kind_id;
		new Ajax.Request(url, {
			method : 'POST',
			parameters : param,
			asynchronous : false,
			onSuccess : function(response) {
				var json = eval("(" + response.responseText + ")");
				for (var i = 0; i < json.length; i++) {
					savedKindIdArray.push(json[i].tid);
				}
			}
		});

		doSearch();
	}
}

function favoriteTreeBoxonDblClick(id) {
	favoriteTreeBoxonSelect(id);
}

function doFavoriteDocRemove() {
	var url = _contextPath + "/edms/tr/seFavoriteDocRemove.action";

	var selectedId;
	var param; 
	
	if(buttonCheck == 1){
		selectedId = mygrid.getSelectedRowId();

		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}

		param = "favoriteDTO.kind_id=" + selectedId;

	}else{
		/**
		 * 2020.12.08(화)
		 * function : doFavoriteDocRemove()
		 * DataView로 인한 기능 분기
		 */
		selectedId = myDataView.getSelected();

		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}

		param = "favoriteDTO.kind_id=" + selectedId.join();
		
	}
	
	new Ajax.Request(url,
			{
				method : 'POST',
				parameters : param,
				asynchronous : false,
				onSuccess : function(response) {
					if ('success' == ('' + response.responseText)) {
						alert($L('TIP_FAVORITE_DOC_REMOVE_COM',
								'즐겨찾기 문서삭제가 완료되었습니다.'));
						favoriteTreeBoxonSelect('-1');
					}
				}
			});
}

function doFavoriteRemove() {
	var kind_id = $("select_searchFormDTO_documentDTO_dockind_id").value;
	var tree_id = $("select_searchFormDTO_documentDTO_tree_id").value;

	if (favoriteSelId == null) {
		alert("즐겨찾기를 선택하여 주시기 바랍니다.");
		return;
	}
	var url = _contextPath + "/edms/tr/seFavoriteRemove.action";
	var param = "favoriteDTO.kind_id=" + kind_id + "&" + "favoriteDTO.tree_id="
			+ tree_id;

	new Ajax.Request(
			url,
			{
				method : 'POST',
				parameters : param,
				asynchronous : false,
				onSuccess : function(response) {
					var firstURL = _contextPath
							+ '/edms/tr/seGetRoleLazeLoadFavorite.action?treeDTO.t_category=T&id=0';
					favoriteTree.deleteChildItems(0);
					favoriteTree.loadXML(firstURL.unescapeHTML());
					favoriteSelId = null;
					alert($L('TIP_FAVORITE_REMOVE_COM', '즐겨찾기삭제가 완료되었습니다.'));
				}
			});
}

function doFavoriteDocAdd() {
	var url = _contextPath + "/edms/tr/seFavoriteDocAdd.action";

	var selectedId;
	var param; 
	
	if(buttonCheck == 1){
		selectedId = mygrid.getSelectedRowId();

		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}

		param = "favoriteDTO.kind_id=" + selectedId;

	}else{
		/**
		 * 2020.12.08(화)
		 * function : doFavoriteDocAdd()
		 * DataView로 인한 기능 분기
		 */
		selectedId = myDataView.getSelected();

		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		
		try {
			var t = selectedId.size();
			param = "favoriteDTO.kind_id=" + selectedId.join();
		} catch (e) {
			param = "favoriteDTO.kind_id=" + selectedId;
		}
		
		
	}

	new Ajax.Request(url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) {
			if ('success' == ('' + response.responseText)) {
				alert($L('TIP_FAVORITE_DOC_ADD_COM', '즐겨찾기 문서추가가 완료되었습니다.'));
			}
		}
	});
}

function doFavoriteAdd() {
	var kind_id = $("select_searchFormDTO_documentDTO_dockind_id").value;
	var tree_id = $("select_searchFormDTO_documentDTO_tree_id").value;
	if (tree_id == "" && kind_id == "") {
		alert($L('TIP_FAVORITE_ADD_ALERT'));
		return;
	}
	var sort_num = j$("#FavoriteDropdown li").length;
	var url = _contextPath + "/edms/tr/seFavoriteAdd.action";

	/*var param = "favoriteDTO.kind_id=" + kind_id + "&" + "favoriteDTO.tree_id="
			+ tree_id+"&favoriteDTO.favorite_nm="+favorite_nm+"&favoriteDTO.sort_num="+sort_num;*/
	var param = "favoriteDTO.kind_id=" + kind_id + "&" + "favoriteDTO.tree_id="
			+ tree_id+"&favoriteDTO.sort_num="+sort_num;
	new Ajax.Request(
			url,
			{
				method : 'POST',
				parameters : param,
				asynchronous : false,
				onSuccess : function(response) {
					if ('success' == ('' + response.responseText)) {
						addFavoriteList();
						alert($L('TIP_FAVORITE_ADD_COM', '즐겨찾기추가가 완료되었습니다.'));
					} else {
						alert($L('TIP_FAVORITE_ADD_ALREADY',
								'이미 즐겨찾기에 추가되어 있습니다.'));
					}
				}
			});

}

/**
 * IE의 경우 현재 Clear and Load 가 되고 있음
 * 분류조회 방식이 현재 선택한 폴더의 내용만 보여줄 경우 deleteRow가 맞으나 하위분류 포함 조회가 된다면 clearAndLoad 방식으로 변경해야 함.
 * 현재 폴더만 조회할 경우 아래 주석
 */
/*분류 조회가 하위 폴더까지 조회하는 경우 Start*/
var dragCount = 0;
var dragTotal = 0;
var dragFail = 0;
/*분류 조회가 하위 폴더까지 조회하는 경우 End*/
function treeBoxInit() {
	
	mytree = tree_layout.cells("b").attachTree();
	mytree.setImagePath( _contextPath + '/dhtmlxSuite/skins/web/imgs/dhxtree_web/');
//	mytree.enableDragAndDrop(true);
	mytree.enableSmartXMLParsing(true);
	mytree.enableDistributedParsing(true,10,250)
	
	var load_url = _contextPath + '/edms/tr/seGetRoleSearchTree.action?treeDTO.t_category=T';
	
	new Ajax.Request(
			load_url,
			{
				method : 'POST',
				asynchronous : true,
				onSuccess : function(response) {
					
					mytree.parse(response.responseText);
					mytree.enableKeyboardNavigation(true);
					mytree.enableHighlighting(true); // is switched off by default
					mytree.setDragBehavior("select");
					
					
					// 트리관리 권한 체크
					/*if(getMenuRole('Tree')){
						mytree.enableContextMenu(myContextMenu);
						mytree.attachEvent("onRightClick", function(id){
							mytree.selectItem(id);
							myContextMenu.setItemText("itemText", mytree.getItemText(id));
						});
					}else{
						
					}*/
						
				}
			}
			);
	
	

	mytree.attachEvent("onDrag", function(sId, tId, id, sObject, tObject){
		
		if(tId == 0){
			alert('드래그하여 해당 폴더에 넣어주십시오.')
		}else{
			if(sObject.object && sObject.object == myDataView){
				
				if(dvContextMenu.isItemHidden('ChangeTree')){
					dndChngTreeRole = false;
				}else{
					dndChngTreeRole = true;
				}
			}
			else if(sObject && sObject == mygrid){
				if(mygrid.___ContextMenu.isItemHidden('ChangeTree')){
					dndChngTreeRole = false;
				}else{
					dndChngTreeRole = true;
				}
				
			}
			
			if(dndChngTreeRole){
				
				//if(tId == 'T0'){
				if(tId == treeTopId){
					alert('최상위 폴더에는 이동할 수 없습니다.');
					return false;
				}
				
				sId_array = new Array();
				$('upTreeAndKind_docInforFormDTO_documentDTO_tree_id').value = tId;
				
				if(sObject.object && sObject.object == myDataView){
					dnd_obj = 'DV';
					var selectedId;
					
					selectedId = myDataView.getSelected();
					
					try {
						
						dragTotal = selectedId.size();
						sId_array = sId;
						PopWin.show('DragTreeChangeDiv');
						PopWin.w('DragTreeChangeDiv').detachStatusBar();
						
					} catch (e) {
						
						sId_array.push(sId);
						PopWin.show('DragTreeChangeDiv');
						
					}
				}
				else if(sObject && sObject == mygrid){
					
					dnd_obj = 'GR';
					var selectedId = mygrid.getSelectedRowId();
					
					sId_array = selectedId.split(',');
					dragTotal = sId_array.size();
					PopWin.show('DragTreeChangeDiv');
					PopWin.w('DragTreeChangeDiv').detachStatusBar();
					
				}else{
					alert("이동할 수 없습니다.");
				}
			}else{
				if(dndRoleAlertCnt == 0){
					dndRoleAlertCnt++;
					alert("다른 지역 사용자는 변경 권한이 없습니다.");
				}else{
					dndRoleAlertCnt = 0;
				}
				
			}
		}
		
		
		
		

	});
	
	mytree.attachEvent("onSelect", function(id){
	    // your code here
		$('select_searchFormDTO_documentDTO_tree_id').value = id;
		$('select_searchFormDTO_treeDTO_tname').value = mytree.getItemText(id);
		treeBoxonSelect(id);
	});
	
	mytree.attachEvent("onDblClick", function(id){
	    // your code here
		$('select_searchFormDTO_documentDTO_tree_id').value = id;
		$('select_searchFormDTO_treeDTO_tname').value = mytree.getItemText(id);
		treeBoxonDblClick(id);
	});
	
	mytree.attachEvent("onLoad", function(){
	    // your code here
		$('select_searchFormDTO_documentDTO_tree_id').value = id;
		$('select_searchFormDTO_treeDTO_tname').value = mytree.getItemText(id);
		treeBoxonLoad();
	});
	
	mytree.attachEvent("onXLE", function(){
		// your code here
		
		//mytree.openItem('T0');
		mytree.openItem(treeTopId);
		//mytree.selectItem(treeTopId);
	});
	
	
}

function MytreeBoxInit() {
	
	mydoctree = tree_layout.cells("c").attachTree();
	mydoctree.setImagePath( _contextPath + '/dhtmlxSuite/skins/web/imgs/dhxtree_web/');
	mydoctree.enableDragAndDrop(true);
	
	mydoctree.setXMLAutoLoadingBehaviour("id");
	mydoctree.setXMLAutoLoading(load_url);
	var load_url = _contextPath + '/edms/tr/seGetRoleMySearchTree.action';
	
	new Ajax.Request(
			load_url,
			{
				method : 'POST',
				asynchronous : true,
				onSuccess : function(response) {
					
					mydoctree.parse(response.responseText);
					mydoctree.enableKeyboardNavigation(true);
					mydoctree.enableHighlighting(true); // is switched off by default
					mydoctree.setDragBehavior("select");
					
					mydoctree.enableContextMenu(myDocContextMenu);
					
					mydoctree.attachEvent("onRightClick", function(id){
						mydoctree.selectItem(id);
						myDocContextMenu.setItemText("itemText", mydoctree.getItemText(id));
					});	
				}
			}
			);
	
	mydoctree.attachEvent("onDrag", function(sId, tId, id, sObject, tObject){
		
		if(tId == '0'){
			alert('드래그하여 해당 폴더에 넣어주십시오.');
		}else{
			sId_array = new Array();
			$('upTreeAndKind_docInforFormDTO_documentDTO_tree_id').value = tId;
			
			if(sObject.object && sObject.object == myDataView){
				dnd_obj = 'DV';
				var selectedId;
				
				selectedId = myDataView.getSelected();
				
				try {
					
					dragTotal = selectedId.size();
					sId_array = sId;
					dndMyTreeChange();
					
				} catch (e) {
					
					sId_array.push(sId);
					
				}
			}
			else if(sObject && sObject == mygrid){
				dnd_obj = 'GR';
				var selectedId = mygrid.getSelectedRowId();
				
				sId_array = selectedId.split(',');
				dragTotal = sId_array.size();
				dndMyTreeChange();
				
			
			/*}else if(sObject && sObject == mydoctree){
				*//**
				 * 분류 트리 끼리 이동
				 * @param tId : 드랍한 분류 / sId : 드래그한 분류
				 * @returns
				 *//*
				if(tId != 0){
					var sourceItemText = mydoctree.getItemText(sId);
					var url = _contextPath + "/edms/tr/mvTreeDrag.action?";
					var param = "mstrTreeFormDTO.targetId="+tId+"&mstrTreeFormDTO.soruceIds="+sId+"&mstrTreeFormDTO.targetCategory=T";
					new Ajax.Request(
							url,
							{
								method : 'POST',
								parameters : param,
								asynchronous : true,
								onSuccess : function(response) {
									console.log(response.responseText);
									if (response.responseText == "ok") {
										
										mydoctree.deleteItem(sId);
										
										mydoctree.insertNewChild(tId,sId,sourceItemText);
										
										
									}else{
										alert("실패");
									}
								}
							});
				}*/
					
				
			}else if(sObject && sObject == mytree){
				
				sId_array = sId;
				dndMyTreeCreate();
				
			}else{
				alert("이동할 수 없습니다.");
			}
		}
		
		
		
		

	});
	
	mydoctree.attachEvent("onDblClick", function(id){
	    // your code here
		if(parent.main_tabbar.tabs('SearchMy') == null){
			parent.main_tabbar.addTab('SearchMy','내문서함');
			parent.main_tabbar.tabs('SearchMy').attachURL("/edms/dc/initSearchMyPage.action");
			parent.main_tabbar.setTabActive('SearchMy');
		} else {
			parent.main_tabbar.setTabActive('SearchMy');
		}
	});
	
}

function treeBoxonXLE(tree, id) {
	if (savedTreeIdArray.length == 0) {
		return;
	}
	if (id == "0") {
		tree.openItem(savedTreeIdArray[0]);
	}
	for (var i = 0; i < savedTreeIdArray.length; i++) {
		if (savedTreeIdArray[i] == id) {
			tree.openItem(savedTreeIdArray[i + 1]);
			savedTreeIdArray.splice(i, 1);
			break;
		}
	}
}

function kindtreeBoxonXLE(tree, id) {
	if (savedKindIdArray.length == 0) {
		return;
	}
	if (id == "0") {
		tree.openItem(savedKindIdArray[0]);
	}
	for (var i = 0; i < savedKindIdArray.length; i++) {
		if (savedKindIdArray[i] == id) {
			tree.openItem(savedKindIdArray[i + 1]);
			savedKindIdArray.splice(i, 1);
			break;
		}
	}
}
function kindBoxInit() {
	kindTree = tree_layout.cells("a").attachTree();
	kindTree.setImagePath( _contextPath + '/dhtmlxSuite/skins/web/imgs/dhxtree_web/');
	kindTree.enableDragAndDrop(false);
	kindTree.enableSmartXMLParsing(true);
	kindTree.enableDistributedParsing(true,10,250)
	
	var load_url = _contextPath + '/edms/tr/seGetRoleSearchTree.action?treeDTO.t_category=K';
	
	new Ajax.Request(
			load_url,
			{
				method : 'POST',
				asynchronous : true,
				onSuccess : function(response) {
					kindTree.parse(response.responseText);
					kindTree.enableKeyboardNavigation(true);
					kindTree.enableHighlighting(true); // is switched off by default
					kindTree.setDragBehavior("select");
				}
			}
			);
	
	kindTree.attachEvent("onSelect", function(id){
	    // your code here
		$('select_searchFormDTO_documentDTO_dockind_id').value = id;
		$('select_searchFormDTO_documentDTO_dockind_name').value = kindTree.getItemText(id);
		kindtreeBoxonSelect(id);
	});
	
	kindTree.attachEvent("onDblClick", function(id){
	    // your code here
		$('select_searchFormDTO_documentDTO_dockind_id').value = id;
		$('select_searchFormDTO_documentDTO_dockind_name').value = kindTree.getItemText(id);
		kindtreeBoxonDblClick(id);
	});
	
	kindTree.attachEvent("onLoad", function(){
	    // your code here
		$('select_searchFormDTO_documentDTO_dockind_id').value = id;
		$('select_searchFormDTO_documentDTO_dockind_name').value = kindTree.getItemText(id);
		kindtreeBoxonLoad();
	});
	
	kindTree.attachEvent("onXLE", function(){
		// your code here
		kindTree.openItem(kindTopId);
		//kindTree.selectItem(kindTopId);
	});
}

function mygridInit() {
	DrawGrid("SS");
	mygrid.attachEvent("onBeforeSorting", function(columnIndex, sortType,
			sortDirection) {
		mygrid.clearAll();
		if (mygrid.__filterParam != undefined)
			gridQString = mygrid.url
					+ (mygrid.url.indexOf("?") >= 0 ? "&" : "?")
					+ mygrid.__filterParam;
		else
			gridQString = mygrid.url;

		mygrid.loadXML(gridQString
				+ (gridQString.indexOf("?") >= 0 ? "&" : "?") + "orderby="
				+ $G(this)._ids[columnIndex] + "&direct=" + sortDirection,function() {
					mygrid.forEachRow(function(id) {

						var updateDate = $G('gridbox').cells(id, 'update_date')
								.getValue();
						$G('gridbox').cells(id, 'update_date').setValue(
								updateDate.replace(/\//gi, '-'));
					});
					grid_select_rid = '';
					grid_select_cind = '';
			});
		mygrid.setSortImgState(true, columnIndex, sortDirection);
		return false;
	});
	mygrid.attachEvent("onFilterStart", function(indexes, values) {
		mygrid.clearAll();
		gridQString = mygrid.url;
		var param = "";
		for (var index = 0; index < indexes.length; index++) {
			var id = $G(this)._ids[indexes[index]];
			var map = new Hash();
			map.set("name", id);
			map.set("value", values[index]);
			
			if (index != 0)
				param += "&";
			param += "filterList.jsonString=" + encodeURI(Object.toJSON(map));
		}
		mygrid.__filterParam = param;
		var callURL = gridQString + (gridQString.indexOf("?") >= 0 ? "&" : "?")
				+ param;
		mygrid.loadXML(gridQString
				+ (gridQString.indexOf("?") >= 0 ? "&" : "?") + param,function() {
			/*
			 * 2014.09.04 WooChul Jung If there is not completed MR, then
			 * change cell color as red
			 */
			mygrid.forEachRow(function(id) {

				var updateDate = $G('gridbox').cells(id, 'update_date')
						.getValue();
				$G('gridbox').cells(id, 'update_date').setValue(
						updateDate.replace(/\//gi, '-'));
				
			});
			
			grid_select_rid = '';
			grid_select_cind = '';
			
		});
		return false;
	});

	function createFilterLoader(f) {
		for (var i = 0; i < mygrid.filters.length; i++)
			if (mygrid.filters[i][1] == f) {
				f = mygrid.filters[i];
				break;
			}
		var obj = f[0];
		if (obj.___id == undefined) {
			obj.___type = f[0].tagName.toLowerCase();
			obj.___id = $G(mygrid)._ids[f[1]];
			obj.___url = mygrid.url;
			obj.___grid = mygrid;
			obj.___col = f[1];
			obj.___CollectValues = function() {
				if (this.___type == "input")
					return;
				var param = "filterName=" + this.___id;
				var ourl = this.url;
				this.url = mygrid.url.replace("/edms/dc/seSearch.action",
						"/edms/dc/seSearchFilter.action");

				if (this.url == ourl) {
					if (this.value == "")
						this.___draw();
					return this.___getValue();
				}

				var that = this;
				new Ajax.Request(this.url, {
					method : 'POST',
					parameters : param,
					onSuccess : function(response) {
						var value = eval('(' + response.responseText + ')');
						that.___value = value;
						that.___draw();
						// add20161125

						// end
					}
				});
				this.value = "";
				return this.___getValue();
			};
			obj.___draw = function() {
				if (this.___type == "select")
					this.___drawSelect();
				if (this.___type == "div")
					this.___drawDiv();
			};
			obj.___getcombos = function() {
				return (this.___grid.combos[this.___col] || (this.___grid._col_combos ? this.___grid._col_combos[this.___col]
						: false));
			};
			obj.___drawSelect = function() {
				var l = this.___getValue();
				var t = this;
				var v = t.value;
				t.innerHTML = "";
				t.options[0] = new Option("", "");
				var f = this.___grid._filter_tr ? this.___grid._filter_tr[c]
						: null;
				for (var j = 0; j < l.length; j++)
					t.options[t.options.length] = new Option(
							f ? f(l[j]) : l[j], l[j]);
				t.value = v;
			};
			obj.___drawDiv = function() {
				var t = this;
				if (!t.combo)
					return; // prevent calls from refreshFilters

				var l = this.___getValue();
				t.combo.clearAll();
				t.combo.render(false);
				var opts = [ [ "", "&nbsp;" ] ];
				for (var j = 0; j < l.length; j++)
					opts.push([ l[j], l[j] ]);
				t.combo.addOption(opts);
				t.combo.render(true);
			};
		}
		obj.___getValue = function() {
			if (this.___value == undefined)
				return [];
			var value = this.___value;
			var l = [];
			var vals = this.___getcombos();

			for (var o = 0; o < value.size(); o++) {
				var d = value[o];
				if (vals.get && vals.get(d)) {
					var gval = vals.get(d);
					if (!gval.startsWith("<img")) {
						d = gval;
					}
				} else if (vals.getOption && vals.getOption(d)) {
					d = vals.getOption(d).text;
				}
				l.push(d);
			}
			return l.sort();
		};
		return obj.___CollectValues();
	}

	mygrid.attachEvent("onBeforePageChanged", function() {
		
		if (!this.getRowsNum())
			return false;
		
	    
		return true;
	});
	
	mygrid.attachEvent("onMouseOver", function(id,ind){
		if(ind == 2){
			
			if (!myPop) {
		        myPop = new dhtmlXPopup();
		    }
			
		    	var view_img = new Image();
		    	view_img.src = _contextPath+"/edmsimages/il"+$G('gridbox').cells(id, 'image_path').getValue();
		    	view_img.width = 700;
		    	view_img.height = 500;
		    	
		    	view_img.onerror = "";
		    	view_img.className=grayScaleClassNm;
		    	view_img.onload = function(){
		    		myPop.attachObject(view_img);
			    	
			        var x = tooltipX-700; // returns left position related to window
			        var y = 200; // returns top position related to window
			        var w = 700;
			        var h = 500;
			        img_tooltip = setTimeout(function(){
			        	myPop.show(x,y,w,h);
			        }, 500);
		    	};
				
			
	    	
		}else{
			clearTimeout(img_tooltip);
			if(myPop)
			myPop.hide();
		}
	});
	
	mygrid.attachEvent("onMouseMove", function(id,ind){
		clearTimeout(img_tooltip);
		if(myPop)
		myPop.hide();
	});
	
	mygrid.attachEvent("onResizeEnd", function(obj){
			console.log("end : " + change_name);
		    inMyPreference(gridChanged("SS",change_name,change_width), "SS");
	});
		
	mygrid.attachEvent("onResize", function(cInd,cWidth,obj){
		change_name = mygrid.getColumnId(cInd);
		change_width = cWidth;
		console.log("resize : " + change_name);
		return true;
	});
}

// 자료정보 버튼관련 추가 2014.2.4 -start
function buttonAdd(win) {
	if (_ses.empDTO.docMgr == "Y") { // 보기, 수정 권한 모두 주었을시

		win.docInfoDiv.attachToolbar.Buttons = [ {
			id : 'Update',
			position : 0,
			text : $L('W_SAVE', '저장'),
			img : 'save.png',
			d_img : 'save.png'
		}, 
		{
			id : 'OneView',
			position : 1,
			text : $L('W_DATA_VIEW', '자료보기'),
			img : 'condition.png',
			d_img : 'condition.png'
		} 
		, {
			id : 'ChangeFile',
			position : 2,
			text : $L('W_FILE_CHANGE', '파일 변경'),
			img : 'modify.png',
			d_img : 'modify.png'
		},
		{
			id : 'ItemAdd',
			position : 3,
			text : "ITEM 추가",
			img : 'add.png',
			d_img : 'add.png'
		}, {
			id : 'ItemSave',
			position : 4,
			text : "ITEM 저장",
			img : 'save.png',
			d_img : 'save.png'
		}, {
			id : 'ItemDelete',
			position : 5,
			text : "ITEM 삭제",
			img : 'delete.png',
			d_img : 'delete.png'
		}, {
			id : 'ReViewDwg',
			position : 6,
			text : $L('W_REVIEW_DWG', '도면비교'),
			img : 'delete.png',
			d_img : 'delete.png'
		}
		
		
		/*{
					id : 'ItemExcelIn',
					position : 2,
					text : $L('W_ITEM_EXCEL_STORE', '설비명 저장'),
					img : 'modify.png',
					d_img : 'modify.png'
				}, {
					id : 'ItemExcelOut',
					position : 3,
					text : $L('W_ITEM_EXCEL_OUT', '설비명 추출'),
					img : 'modify.png',
					d_img : 'modify.png'
				}*/
		];
		
	} else{
		win.docInfoDiv.attachToolbar.Buttons = [{
			id : 'ReViewDwg',
			position : 1,
			text : $L('W_REVIEW_DWG', '도면비교'),
			img : 'WF.png',
			d_img : 'WF.png'
		}
		];
	}
}

var checkDowloadInteval = null;
var token = null;


function checkExt() {
	var EX_FORMAT = "\.(xls|xlsx)$";
	if ((new RegExp(EX_FORMAT, "i")).test(j$('#fileSelecter').val()))
		return true;
	alert($L('W_IS_NOT_EXCELFILE'));
	return false;
}


function convert(str) {
	str = str.replace(/&/g, "&amp;");
	str = str.replace(/>/g, "&gt;");
	str = str.replace(/</g, "&lt;");
	str = str.replace(/"/g, "&quot;");
	str = str.replace(/'/g, "&#039;");
	return str;
}

function RCheck(value) {
	if (value == true) {
		document.getElementById('rtsCompany').style.display = "";
		document.getElementById('rtstreeid').style.display = "";
		$('inCheckOutDoc_checkOutDTO_checkout_path').value = "";
	} else {
		$('inCheckOutDoc_checkOutDTO_company_id_hidden').value = "";
		//$('inCheckOutDoc_checkOutDTO_checkout_path').value = "";
	}
	$('inCheckOutDoc_checkOutDTO_commts').value = "";
}

// POST 방식 새창
function doNewOpen(id) {

	var selectedId = mygrid.getSelectedRowId();

	if (selectedId == null) {
		alert($L('SN_D233'));
		return;
	}

	selectedId = $w(selectedId.gsub(',', ' '));

	var viewCheck = false;
	Openfile.deleteTemp();
	for (var i = 0; i < selectedId.size(); i++) {

		doc_seq = $G('gridbox').cells(selectedId[i], 'doc_seq').getValue();
		doc_no = $G('gridbox').cells(selectedId[i], 'doc_no').getValue();

		file_path = newOpendocview(doc_seq, doc_no);
		if (isRapidViewUsedExt(Openfile.parsingExt(file_path))) {
			if (Openfile.parsingExt(file_path) == "pdf") {
				openWindow(_contextPathURL + "/Pdf_View.jsp?file_path="
						+ encodeURI(encodeURIComponent(file_path))
						+ "&doc_seq=" + doc_seq, doc_seq, "", "");
			} else {
				openWindow(_contextPathURL + "/View.jsp?file_path="
						+ encodeURI(encodeURIComponent(file_path))
						+ "&doc_seq=" + doc_seq, doc_seq, "", "");
			}
		} else {
			document.OpenFtp.fsexecPgm(file_path);
		}
	}
}

/*
 * workhis 배포이력담기
 */
function setWorkHis(doc_id) {
	var Url = _contextPath + "/edms/ht/inWorkHistory.action";
	var param = "historyFormDTO.documentDTO.doc_seq=" + doc_id
			+ "&historyFormDTO.workhistoryDTO.kind=" + 'DI';
	var res = "";
	new Ajax.Request(Url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) {
		}
	});
}

/* 기배포목록 팝업 */
function doOldOutDistribute() {
	// selectOldOut();
	// PopWin.show('OldOutDiv');
}

function doTagReportDivExcel() {
	var count = grid7.getRowsNum();
	if (count == 0) {
		alert($L('SN_NO_DATA'));
		return;
	}
	grid7.toExcel(_contextPath + "/ExcelGenerator");
}

/*
 * 2016.10.07 Kijoo Kim retrieve equipment class in SAP.
 */
function doEquip(iTidnr) {

	// alert(iTidnr);
	var url = _contextPath + "/edms/dc/seEquipClss.action";
	var param = "iTidnr=" + iTidnr;
	new Ajax.Request(
			url,
			{
				method : 'POST',
				parameters : param,
				asynchronous : true,
				onSuccess : function(response) {
					var json = eval('(' + response.responseText + ')');

					if (json.length > 0) {
						j$('#equipClass').empty();
						for (var i = 0; i < json.length; i++) {
							var td1 = '<td class="tdLabel" style="text-align: right;padding-right:10px">'
									+ json[i].smbez + '</td>';
							var td2 = '<td>' + json[i].ausp1 + json[i].dime1
									+ '</td>';
							j$('<tr>' + td1 + td2 + '</tr>').appendTo(
									j$('#equipClass'));
						}

						PopWin.show('equipClassDiv');
					}
				}
			});
}

function doSearchExcelDown() {
	PopWin.show('ExcelDiv');
	
	//mygrid.toExcel(_contextPath + "/ExcelGenerator");

	/*var selectedId = mygrid.getAllRowIds();
	selectedId = $w(selectedId.gsub(',', ' '));
	if (selectedId.size() == 0) {
		alert($L("NOT_SEARCHING_MSG"));
		return;
	}

	var param = ClearParameter(Form.serialize('select', true));
	var arr = [ "doc_type", "doc_no", "rev_no", "doc_name", "checkout_info",
			"update_date", "tree_id", "dockind_id", "commts" ];

	gridtemp = "&searchFormDTO.gridNames=" + $G('gridbox').getDisplayIds(); // +"&curpage="+curpage
	url = _contextPath + "/edms/dc/searchExcel.action?" + gExcelParam + gridtemp;
	var gridtemp = "";
	window.open(url);*/
}

// add20161128
function showDocItemInfo() {
	// PopWin.w('docInfoDiv').setModal(false);
	docnogrid.clearAll();
	PopWin.show('DocItemInfoDiv');
	PopWin.w('DocItemInfoDiv').button("close").attachEvent("onClick",
			function() {
				// PopWin.w('docInfoDiv').setModal(true);
				resultRemove();
				docitemaddlist();
			});

}

// Tag 등록 팝업창 화면에 표시
function showTagInfo() {
	PopWin.w('docInfoDiv').setModal(false);
	searchTagInfo();
	
	PopWin.w('tagInfoDiv').button("close").attachEvent("onClick", function() {
		PopWin.w('docInfoDiv').setModal(true);
		seItemList();
		resultRemove();
	});

	

}

// Item 조회 메서트
function searchTagInfo() {
	var gridQString = _contextPath + "/edms/dc/seTagInfo.action?";
	var param = "itemDTO.doc_seq="
			+ $('docInforFormDTO_documentDTO_doc_seq').value;
	tagInfoGrid.clearAndLoad(gridQString + param + "&itemDTO.gridNames=" + $G('tagInfogridbox').getIds(),
			function() {
				waiter.hide();
			});
	PopWin.show('tagInfoDiv');
}

function seItemList() {
	itemInforgrid.clearAll();
	gridQString = _contextPath + "/edms/dc/seItemList.action?";
	param = "historyFormDTO.documentDTO.doc_seq="
			+ $('docInforFormDTO_documentDTO_doc_seq').value;
	itemInforgrid.loadXML(gridQString + param + "&historyFormDTO.gridNames="
			+ $G('itemInforgridBox').getIds(), null);
}



// add20161124 나만의도면보기추가
function doMyViewAdd() {
	var rowIds
	
	if(buttonCheck == 1){
		rowIds = mygrid.getSelectedRowId();
		if (rowIds == null) {
			alert($L('SN_D233'));
			return;
		}
		rowIds = $w(rowIds.gsub(',', ' '));
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doMyViewAdd()
		 * DataView로 인한 기능 분기
		 */
		
		rowIds = myDataView.getSelected();
		if (rowIds == null) {
			alert($L('SN_D233'));
			return;
		}
		
		try {
			var t = rowIds.size();
			rowIds = rowIds.join();
		} catch (e) {
			rowIds = rowIds;
		}
		
	}
	

	var emp_id = _ses.ses_emp_id;
	url = _contextPath + "/edms/dc/AddView.action";
	var param = "doc_seq=" + rowIds + "&emp_id=" + emp_id;

	new Ajax.Request(url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) {
			if ('success' == ('' + response.responseText)) {
				alert($L('SN_I038', '성공적으로 저장되었습니다.'));
			} else {
				alert($L('SN_E081', '작업을 실패했습니다.'));
			}
		}
	});

}

function InterfaceSearch(bid) {

	mygrid.enablePaging(true,seSearchCount("SC"),10,"page",true,"infoArea");
	mygrid.setPagingSkin("bricks")

	var param = null;
	$('Search_desc').style.display = "none";
	if (!$G('gridbox').isColumnHidden('result')) {
		$G('gridbox').setColumnHidden('result', true);
		$G('gridbox').setColumnValue('result', "");
	}
	if ($('select_searchFormDTO_documentDTO_i_user_name').value == "") {
		$('select_searchFormDTO_documentDTO_i_user_id').value = "";
	}
	if ($('select_searchFormDTO_documentDTO_u_user_name').value == "") {
		$('select_searchFormDTO_documentDTO_u_user_id').value = "";
	}
	param = ClearParameter(Form.serialize('select', true));
	Cookies.set("search", encodeURIComponent(param.gsub('+', ' ')));

	for (var i = 0; i < mygrid.filters.size(); i++) {
		if (mygrid.filters[i][0].tagName == "INPUT")
			mygrid.filters[i][0].value = "";
	}

	gExcelParam = param;

	gridQString = _contextPath + "/edms/dc/seISearch.action?";
	mygrid.clearAndLoad(gridQString + param + "&searchFormDTO.gridNames="
			+ $G('gridbox').getIds() + "&emp_id=" + _ses.ses_emp_id,
			function() {
				/*
				 * 2014.09.04 WooChul Jung If there is not completed MR, then
				 * change cell color as red
				 */
				mygrid.forEachRow(function(id) {
					var updateDate = $G('gridbox').cells(id, 'update_date')
							.getValue();
					$G('gridbox').cells(id, 'update_date').setValue(
							updateDate.replace(/\//gi, '-'));

					// waiter.show();
				});
				doClearCookie(false);
				// checkDetailSearch();
				PopWin.hide('searchDiv');
				// waiter.hide();
			});
	bHilightList = new Array();
	checkDetailSearch();
	if (bid == "search" || bid == undefined || bid.keyCode == "13") {
		var tid = $('select_searchFormDTO_documentDTO_tree_id').value;
		tid = (tid == "" ? "T1" : tid);
		// changeDocCnt(kindTree, tid, "T");
		var dockind_id = $('select_searchFormDTO_documentDTO_dockind_id').value;
		dockind_id = (dockind_id == "" ? "0" : dockind_id);
		// changeDocCnt(mytree, dockind_id, "K");

	}
	// waiter.show();
}

function doRevSearch() {
	var selectedId = mygrid.getSelectedRowId();
	if (selectedId == null) {
		alert("파일을 선택해 주세요.");
		return;
	}

	var selectCheck = selectedId.split(",");

	if (selectCheck.length > 1) {
		alert("하나의 파일만 선택해 주세요.");
		return;
	}

	mygrid.enablePaging(true,seSearchCount("SC"),10,"page",true,"infoArea");
	mygrid.setPagingSkin("bricks")

	var param = null;
	$('Search_desc').style.display = "none";
	if (!$G('gridbox').isColumnHidden('result')) {
		$G('gridbox').setColumnHidden('result', true);
		$G('gridbox').setColumnValue('result', "");
	}
	
	var current_tree_sub = $('select_searchFormDTO_documentDTO_tree_sublevel').value;
	var current_tree_id = $('select_searchFormDTO_documentDTO_tree_id').value;

	$('select_searchFormDTO_documentDTO_doc_no').value = $G('gridbox').cells(
			selectedId, 'doc_no').getValue();
	$('select_searchFormDTO_revisionA').checked = true;
	$('select_searchFormDTO_revisionM').checked = false;
	$('select_searchFormDTO_documentDTO_tree_id').value = "T0";
	if(uniqueKeyOption == 2){
		$('select_searchFormDTO_documentDTO_tree_sublevel').value = 'cur';
		$('select_searchFormDTO_documentDTO_tree_id').value = $G('gridbox').cells(
				selectedId, 'tree_id').getValue();
	}
	$('select_searchFormDTO_documentDTO_com_doc_no').value = "";
	$('select_searchFormDTO_documentDTO_doc_type').checked = true;
	$('select_searchFormDTO_documentDTO_grade_id').value = "";
	$('select_searchFormDTO_item').value = "";
	$('select_searchFormDTO_documentDTO_doc_name').value = "";
	$('select_searchFormDTO_documentDTO_maintain_id').value = "";
	$('select_searchFormDTO_documentDTO_project_name').value = "";
	$('select_searchFormDTO_documentDTO_i_user_id').value = "";
	$('select_searchFormDTO_documentDTO_u_user_id').value = "";
	$('select_searchFormDTO_documentDTO_ext').value = "";
	$('select_searchFormDTO_documentDTO_lock_statusA').checked = true;
	$('select_searchFormDTO_documentDTO_commts').value = "";
	

	param = ClearParameter(Form.serialize('select', true));
	Cookies.set("search", encodeURIComponent(param.gsub('+', ' ')));

	for (var i = 0; i < mygrid.filters.size(); i++) {
		if (mygrid.filters[i][0].tagName == "INPUT")
			mygrid.filters[i][0].value = "";
	}

	gExcelParam = param;
	paramStr = param;
	gridQString = _contextPath + "/edms/dc/seSearch.action?";
	mygrid.clearAndLoad(gridQString + param + "&searchFormDTO.gridNames="
			+ $G('gridbox').getIds() + "&emp_id=" + _ses.ses_emp_id,
			function() {
				mygrid.forEachRow(function(id) {
					var updateDate = $G('gridbox').cells(id, 'update_date')
							.getValue();
					$G('gridbox').cells(id, 'update_date').setValue(
							updateDate.replace(/\//gi, '-'));
				});

				$('select_searchFormDTO_documentDTO_tree_sublevel').value = current_tree_sub;
				$('select_searchFormDTO_documentDTO_tree_id').value = current_tree_id;
				
				$('select_searchFormDTO_documentDTO_doc_no').value = "";
				$('select_searchFormDTO_revisionM').checked = true;
				$('select_searchFormDTO_revisionA').checked = false;

				doClearCookie(false);
				
				PopWin.hide('searchDiv');
			});
	checkDetailSearch();
}

function doBagList() {
	mygrid.enablePaging(true,seSearchCount("SC"),10,"page",true,"infoArea");
	mygrid.setPagingSkin("bricks")

	gridQString = _contextPath + "/edms/dc/AddListSearch.action?";
	mygrid.clearAndLoad(gridQString + "&searchFormDTO.gridNames="
			+ $G('gridbox').getIds() + "&emp_id=" + _ses.ses_emp_id,
			function() {
				doClearCookie(false);
				PopWin.hide('searchDiv');
			});
}

function doBagAdd() {
	
	var rowIds;
	
	if(buttonCheck == 1){
	
		rowIds = mygrid.getSelectedRowId();
		if (rowIds == null) {
			alert($L('SN_D233'));
			return;
		}
		rowIds = $w(rowIds.gsub(',', ' '));

	}
	else
	{
		/**
		 * 2020.12.08(화)
		 * function : doBagAdd()
		 * DataView로 인한 기능 분기
		 */
	
		rowIds = myDataView.getSelected();
		if (rowIds == null) {
			alert($L('SN_D233'));
			return;
		}
		
		try {
			var t= rowIds.size();
			
			rowIds = rowIds.join();
		} catch (e) {
			rowIds = rowIds;
		}
		
	}
	
	var emp_id = _ses.ses_emp_id;
	url = _contextPath + "/edms/dc/AddBag.action";
	var param = "doc_seq=" + rowIds + "&emp_id=" + emp_id + "&type=N";

	new Ajax.Request(url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) {
			if ('success' == ('' + response.responseText)) {
				alert($L('SN_I038', '성공적으로 저장되었습니다.'));
			} else {
				alert($L('SN_E081', '작업을 실패했습니다.'));
			}
		}
	});
}

function doBagDelete() {
	var rowIds;
	
	if(buttonCheck == 1){
	
		rowIds = mygrid.getSelectedRowId();
		if (rowIds == null) {
			alert($L('SN_D233'));
			return;
		}
		rowIds = $w(rowIds.gsub(',', ' '));

	}
	else
	{
		/**
		 * 2020.12.08(화)
		 * function : doBagDelete()
		 * DataView로 인한 기능 분기
		 */
	
		rowIds = myDataView.getSelected();
		if (rowIds == null) {
			alert($L('SN_D233'));
			return;
		}
		try {
			var t = rowIds.size();
			rowIds = rowIds.join();
		} catch (e) {
			rowIds = rowIds;
		}
		
	}

	var emp_id = _ses.ses_emp_id;
	url = _contextPath + "/edms/dc/RemoveBag.action";
	var param = "doc_seq=" + rowIds + "&emp_id=" + emp_id + "&type=N";

	new Ajax.Request(url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) {
			if ('success' == ('' + response.responseText)) {
				alert($L('SN_I029', '성공적으로 삭제되었습니다.'));
				doBagList();
			} else {
				alert($L('SN_E081', '작업을 실패했습니다.'));
			}
		}
	});
}

// 외주배포 버튼 클릭 시 팝업창 띄우기
function doOutPublish() {
	var rowIds;
	
	if(buttonCheck == 1){
	
		rowIds = mygrid.getSelectedRowId();
		if (rowIds == null) {
			alert($L('SN_D233'));
			return;
		}
		rowIds = $w(rowIds.gsub(',', ' '));

	}
	else
	{
		/**
		 * 2020.12.08(화)
		 * function : doOutPublish()
		 * DataView로 인한 기능 분기
		 */
		rowIds = myDataView.getSelected();
		if (rowIds == null) {
			alert($L('SN_D233'));
			return;
		}
		
		try {
			var t = rowIds.size();
			rowIds = rowIds.join();
		} catch (e) {
			rowIds = rowIds;
		}
		
	}

	PopWin.hide('OutPublishDiv');
	var emp_id = _ses.ses_emp_id;
	var gridQString = _contextPath + "/edms/dc/seOutPublishDoc.action?";
	OPgrid.clearAndLoad(gridQString + "&searchFormDTO.doc_seq_list=" + rowIds
			+ "&searchFormDTO.gridNames3=" + $G('OPgridbox').getIds(), 
			function(id){
		OPgrid.checkAll(true);
	});
	PopWin.show('OutPublishDiv');
	PopWin.w('OutPublishDiv').button("close").attachEvent("onClick",function(){
		j$("#OutPublish")[0].reset();
		$('OutPublish_workFlowFormDTO_outSourcingDTO_comment').value = "";
	});
			
}

// 외주배포 팝업창 -> 외주담당자 리스트 조회
function CompanyChk() {
	if ($('OutPublish_workFlowFormDTO_outSourcingDTO_company_code').value == ""
			|| $('OutPublish_workFlowFormDTO_outSourcingDTO_company_code').value == null) {
		j$("#Distributor_list option").remove();
		j$("#Distributor_list")
				.append("<option value=''>외주업체를 선택해주세요</option>");
	} else {
		var url = _contextPath + "/edms/dc/seApprovalInfo.action";
		var param = "company_code="
				+ $('OutPublish_workFlowFormDTO_outSourcingDTO_company_code').value;

		new Ajax.Request(url, {
			method : "POST",
			parameters : param,
			asynchronous : false,
			onSuccess : function(response) {
				var AppList = eval("(" + response.responseText + ")");
				j$("#Distributor_list option").remove();
				for (var i = 0; i < AppList.length; i++) {
					j$("#Distributor_list").append(
							"<option value='" + AppList[i].emp_id + "'>"
									+ AppList[i].emp_name + "</option>");
				}
			}
		});
	}

}

// 외주배포용 파일리스트 선택 삭제
function doDocDelete() {
	var rId = OPgrid.getCheckedRows(0);

	if (rId == "" || rId == null) {
		alert("파일을 선택해 주세요");
		return;
	}

	rId = $w(rId.gsub(',', ' '));
	for (var i = 0; i < rId.size(); i++) {
		OPgrid.deleteRow(rId[i]);
	}

}

// 외주배포 승인요청
function OutPublish_Request() {
	var rId = OPgrid.getCheckedRows(0);

	if (rId == "" || rId == null) {
		alert("파일을 선택해 주세요");
		return;
	}
	rId = $w(rId.gsub(',', ' '));

	if ($('OutPublish_workFlowFormDTO_outSourcingDTO_company_code').value == ""
			|| $('OutPublish_workFlowFormDTO_outSourcingDTO_company_code').value == null) {
		alert("외주업체를 선택해주세요");
		return;
	}
	if (j$("#Distributor_list").val() == ""
			|| j$("#Distributor_list").val() == null) {
		alert("외주담당자를 선택해주세요");
		return;
	}
	if (j$("#expired_date").val() == ""
			|| j$("#expired_date").val() == null) {
		alert("조회만료기한을 지정해주세요");
		return;
	}

	if ($('OutPublish_workFlowFormDTO_outSourcingDTO_print_max_cnt').value == ""
			|| $('OutPublish_workFlowFormDTO_outSourcingDTO_print_max_cnt').value == null) {
		alert("최대출력 횟수 지정해주세요");
		return;
	} else {
		var print_max_cnt = $('OutPublish_workFlowFormDTO_outSourcingDTO_print_max_cnt').value;
		if (isNaN(print_max_cnt)) {
			alert("숫자만 입력가능합니다.");
			return;
		}
	}

	if ($('OutPublish_workFlowFormDTO_outSourcingDTO_down_max_cnt').value == ""
			|| $('OutPublish_workFlowFormDTO_outSourcingDTO_down_max_cnt').value == null) {
		alert("최대다운 횟수 지정해주세요");
		return;
	} else {
		var down_max_cnt = $('OutPublish_workFlowFormDTO_outSourcingDTO_down_max_cnt').value;
		if (isNaN(down_max_cnt)) {
			alert("숫자만 입력가능합니다.");
			return;
		}
	}

	if (validateApprover($('OutPublish').IdObs) == false) {
		alert($L('SN_SE_APP'));
		return;
	}

	$('OutPublish_workFlowFormDTO_outSourcingDTO_doc_seq_list').value = rId;
	$('OutPublish_workFlowFormDTO_outSourcingDTO_distributor_id').value = _ses.ses_emp_id;
	$('OutPublish_workFlowFormDTO_outSourcingDTO_emp_id').value = j$("#Distributor_list").val();
	$('OutPublish_workFlowFormDTO_outSourcingDTO_expired_date').value = j$("#expired_date").val();

	var url = _contextPath + "/edms/wf/Request.action";
	var param = Form.serialize('OutPublish');

	// param = param + "&workFlowFormDTO.appList.app_user=" + _ses.empDTO.emp_id
	// ;

	new Ajax.Request(url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) {
			if (response.responseText != "ERROR") {
				alert($L('SN_I023'));
			}
		}
	});
	PopWin.hide('OutPublishDiv');
	j$("#OutPublish")[0].reset();

}

// 외주 변경요청창 띄우기
function doOutChangeRequest() {
	changeStatus = "ORC";
	
	var selectedId;
	
	if(buttonCheck == 1){
		
		selectedId = mygrid.getSelectedRowId();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		$('OutRequest_workFlowFormDTO_doc_seq').value = selectedId; // 2014.2.12 수정
		// doc_seq에 파일을 넘겨주는 부분

		var gridQString = _contextPath + "/edms/dc/seRCDocList.action?";
		var param = "searchFormDTO.doc_seq_list=" + selectedId;
		ORCgrid.clearAndLoad(gridQString + param + "&searchFormDTO.gridNames="
				+ $G('OutReqChangegridbox').getIds(), function() {
		});
		
		downloadId = $w(selectedId.gsub(',', ' '));
		
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doOutChangeRequest()
		 * DataView로 인한 기능 분기
		 */
		selectedId = myDataView.getSelected();
		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		
		
		try {
			// 다수 선택일 경우
			var t = selectedId.size();
			selectedId = selectedId.join();
			
			$('OutRequest_workFlowFormDTO_doc_seq').value = selectedId; // 2014.2.12 수정
			// doc_seq에 파일을 넘겨주는 부분
			
			var gridQString = _contextPath + "/edms/dc/seRCDocList.action?";
			var param = "searchFormDTO.doc_seq_list=" + selectedId;
			ORCgrid.clearAndLoad(gridQString + param + "&searchFormDTO.gridNames="
					+ $G('OutReqChangegridbox').getIds(), function() {
			});
			
			downloadId = $w(selectedId.gsub(',', ' '));
			
		} catch (e) {
			// 한개 션택일 경우
			selectedId = selectedId;
			
			$('OutRequest_workFlowFormDTO_doc_seq').value = selectedId; // 2014.2.12 수정
			// doc_seq에 파일을 넘겨주는 부분
			
			var gridQString = _contextPath + "/edms/dc/seRCDocList.action?";
			var param = "searchFormDTO.doc_seq_list=" + selectedId;
			ORCgrid.clearAndLoad(gridQString + param + "&searchFormDTO.gridNames="
					+ $G('OutReqChangegridbox').getIds(), function() {
			});
			
			downloadId = $w(selectedId.gsub(',', ' '));
		}
		
		
		
	}
	
	
	downloadCnt = 0;
	downloadTotalCnt = downloadId.size();
	
	
	markupFiles.length = 0;
	attachFiles.length = 0;
	
	outAttachgridBox.clearAll();

	PopWin.show('OutReqChangeDiv');
	PopWin.w('OutReqChangeDiv').button("close").attachEvent("onClick",function() {
		j$("#OutRequest")[0].reset();
		$('OutRequest_workFlowFormDTO_workflowAppDTO_remark').value = "";
		TempFolderDelete();
	});
}

// 외주변경요청 팝업창 -> 외주담당자 리스트 조회
function OutCompanyChk() {
	if ($('OutRequest_workFlowFormDTO_outSourcingDTO_company_code').value == ""
			|| $('OutRequest_workFlowFormDTO_outSourcingDTO_company_code').value == null) {
		j$("#out_emp_list option").remove();
		j$("#out_emp_list").append("<option value=''>외주업체를 선택해주세요</option>");
	} else {
		var url = _contextPath + "/edms/dc/seApprovalInfo.action";
		var param = "company_code="
				+ $('OutRequest_workFlowFormDTO_outSourcingDTO_company_code').value;

		new Ajax.Request(url, {
			method : "POST",
			parameters : param,
			asynchronous : false,
			onSuccess : function(response) {
				var AppList = eval("(" + response.responseText + ")");
				j$("#out_emp_list option").remove();
				for (var i = 0; i < AppList.length; i++) {
					j$("#out_emp_list").append(
							"<option value='" + AppList[i].emp_id + "'>"
									+ AppList[i].emp_name + "</option>");
				}
			}
		});
	}
}

//외주 마크업
function OutMarkup() {
	
	var selectedId = ORCgrid.getSelectedRowId();
	
	if (selectedId == null) {
		alert($L('SN_D233'));
		return;
	}
	
	selectedId = $w(selectedId.gsub(',', ' '));
	
	if(selectedId.size() > 1)
	{
		alert("하나의 자료를 선택하여 주시기 바랍니다.");
		return;
	}
	
	var ext = $G('OutReqChangegridbox').cells(selectedId[0], 'ext').getValue();
	if (ext.substring(0,Number(ext.length-1)).toLowerCase() != "dwg" &&
			ext.substring(0,Number(ext.length-1)).toLowerCase() != "tif") {
		alert($L('SN_NOT_SUPPORT_EXTENSION'));
		return;
	}
	
	var isExist = (markupFiles.indexOf(selectedId[0])!== -1);
	if(isExist == false)
	{
		markupFiles.push(selectedId[0]);
	}
	
	var param = "?socketDTO.f_category_id="
			+ escape($G('OutReqChangegridbox').cells(selectedId,'doc_seq').getValue());
	var url = _contextPath + "/edms/socket/seDocumentOpen.action";
	new Ajax.Request(url, {
		method : 'POST',
		parameters : param.toQueryParams(),
		asynchronous : false,
		onSuccess : function(response) {
			DocumentOpen(response.responseText);
		}
	});
}

//외주수정요청 첨부파일 팝업창 열기
function doOutReqChangeDivAttachList()
{
	var rowIds = ORCgrid.getSelectedRowId();
	
	if (rowIds == null) {
		alert($L("SN_D233"));
		return;
	}
	rowIds = $w(rowIds.gsub(',', ' '));
	if(rowIds.size() > 1){
		alert("파일 1개만 선택가능합니다.");
		return;
	}
	PopWin.w('OutReqChangeDiv').setModal(false);
	PopWin.w('outReqChangeDivAttachList').setModal(true);
	PopWin.w('outReqChangeDivAttachList').show();
}

//외주수정요청 첨부파일저장
function doOutReqChangeDivAttachListSave()
{
	PopWin.w('outReqChangeDivAttachList').setModal(false);
	PopWin.w('outReqChangeDivAttachList').hide();
}

//외주수정요청 첨부파일 추가
function doOutReqChangeDivAttachListAdd()
{
	changeStatus = "ORC";
	FileSelect();
}

function CallBackAddRowORC(data) {
	
	files = data.files;
	if (files == null)
		return;
	
	var i=0;
	
	var isExist = (attachFiles.indexOf($G('OutReqChangegridbox').cells(ORCgrid.getSelectedRowId(), 'doc_seq').getValue())!== -1);
	if(isExist == false)
	{
		attachFiles.push($G('OutReqChangegridbox').cells(ORCgrid.getSelectedRowId(), 'doc_seq').getValue());
	}
	files.forEach(function(id){
		var row = $G('outAttachBox').defValue;
		var newID = outAttachgridBox.uid();
		row.set('seq', i);
		row.set('attach_name', $G('OutReqChangegridbox').cells(ORCgrid.getSelectedRowId(), 'doc_no').getValue());
		row.set('file_path', files[i].localpath);
		row.set('doc_seq', $G('OutReqChangegridbox').cells(ORCgrid.getSelectedRowId(), 'doc_seq').getValue());
		$G('outAttachBox').addRow(newID, row);
		console.log(files[i].localpath);
		i++;
	})
	$G('OutReqChangegridbox').cells(ORCgrid.getSelectedRowId(), 'attach_yn').setValue("Y");
}

//외주수정요청 첨부파일 삭제
function doOutReqChangeDivAttachListDelete()
{
	outAttachgridBox.deleteSelectedRows();

	var allId = outAttachgridBox.getAllRowIds();

	attachFiles.forEach(function(id) {
		$G('OutReqChangegridbox').cells(id, 'attach_yn').setValue("N");
	})
	allId = $w(allId.gsub(',', ' '));
	attachFiles = [];
	if (allId != "") {
		allId.forEach(function(id) {
			var isExist = (attachFiles.indexOf($G('OutReqChangegridbox').cells(
					$G("outAttachBox").cells(id, "doc_seq").getValue(), 'doc_seq').getValue()) !== -1);
			if (isExist == false) {
				attachFiles.push($G('OutReqChangegridbox').cells(
						$G("outAttachBox").cells(id, "doc_seq").getValue(), 'doc_seq').getValue());
				$G('OutReqChangegridbox').cells($G("outAttachBox").cells(id, "doc_seq").getValue(), 'attach_yn').setValue("Y");
			}
		})
	}
}

// 외주수정 승인요청
function OutReqChange() {
	
	if ($('OutRequest_workFlowFormDTO_outSourcingDTO_company_code').value == ""
			|| $('OutRequest_workFlowFormDTO_outSourcingDTO_company_code').value == null) {
		alert("외주업체를 선택해주세요");
		return;
	}
	if (j$("#out_emp_list").val() == "" || j$("#out_emp_list").val() == null) {
		alert("외주담당자를 선택해주세요");
		return;
	}
	if (j$("#expired_change_date").val() == ""
		|| j$("#expired_change_date").val() == null) {
	alert("조회만료기한을 지정해주세요");
	return;
	}

	$('OutRequest_workFlowFormDTO_outSourcingDTO_expired_change_date').value = j$("#expired_change_date").val();
	$('OutRequest_workFlowFormDTO_workflowDTO_emp_id').value = j$(
			"#out_emp_list").val();
	var emp_name = j$("#out_emp_list").text();

	
	 swal({
			title : '',
			text : $L("SN_C018"),
			type : 'info',
			showCancelButton : true,
			confirmButtonClass : "btn-danger",
			confirmButtonText : "예",
			cancelButtonText : "아니오",
			closeOnConfirm : true,
			closeOnCancel : true
		}, function(isConfirm) {
			if (isConfirm) {
				if (validateApprover($('OutRequest').IdObs) == false) {
					this.closeOnConfirm = false;
					alert($L('SN_SE_APP'));
					return;
				}

			/*	downloadId = ORCgrid.getAllRowIds();
				downloadId = $w(downloadId.gsub(',', ' '));

				downloadCnt = 0;
				
				outReqChangeSend();*/
				downloadId = markupFiles;
				attachId = outAttachgridBox.getAllRowIds();
				attachId = $w(attachId.gsub(',', ' '));
				downloadCnt = 0;
				attachCnt = 0;
				
				console.log(downloadId.size());
				console.log(downloadId.size() > 0);
				if(downloadId.size() > 0)
				{	
					outReqChangeSend();
				}else{
					moveNextRowORC(null);
				}
			}else{
				return;
			}

		});
	
}

function outReqChangeSend()
{
	console.log(changeStatus);
	changeStatus = "ORC";
	var rId = downloadId[downloadCnt];
	Openfile.addMkuFileByFileName($G("OutReqChangegridbox").cells(rId, "doc_seq").getValue(),rId);
}

function CallBackSaveRowsORC(data,requestData) {
	if (changeStatus == "ORCATTACH") {
		attachCnt++;
		attachFileUploadORC();
	} else {
		moveNextRowORC(data,requestData);
	}
}

function moveNextRowORC(data,requestData) {
	if (data != null) {
		if(data.status.status != "S")
		{
			return_fid += "0"+",";
			var param = "?fileDTO.file_id="+escape(requestData.body.files[0].filename);
			var url = _contextPath + "/edms/fi/deFileRow.action";
			new Ajax.Request(url, {
				method : "POST",
				parameters : param.toQueryParams(),
				asynchronous : false,
				onSuccess : function(response) {
		
				}
			});
		}else{
			return_fid += requestData.body.files[0].filename+",";
		}
	}
	downloadCnt++;
	
	if (downloadCnt < downloadId.size()) {
			setTimeout(outReqChangeSend, 30);
	} 
	else {
		if(return_fid != "")
		{
			$('OutRequest_workFlowFormDTO_workflowAppDTO_markup_list').value = return_fid.substring(0,return_fid.lastIndexOf(","));
		}
		
		var url = _contextPath + "/edms/dc/seOCODocDupChk.action";
		var param = Form.serialize('OutRequest');

		new Ajax.Request(url, {
			method : 'POST',
			parameters : param,
			asynchronous : false,
			onSuccess : function(response) {
				if (response.responseText == "ERROR") {
					alert("해당 외주담당자에게 \n수정 요청된 문서가 포함되어있습니다.");
					return;
				} else {
					var url2 = _contextPath + "/edms/wf/Request.action";
					var param2 = Form.serialize('OutRequest');

					new Ajax.Request(url2, {
						method : 'POST',
						parameters : param2,
						asynchronous : false,
						onSuccess : function(response) {
							var json = eval('(' + response.responseText + ')');
							if (json.workflowDTO != undefined
									&& json.workflowDTO.work_seq != undefined) {
								attach_seq = json.workflowDTO.work_seq;
								markup_seq = json.workflowDTO.work_seq;
							}
						}
					});
				}
			}
		});
		return_fid = "";
		attachFileUploadORC();
		//j$("#OutRequest")[0].reset();
		//PopWin.hide('OutReqChangeDiv');
	}
}

function attachFileUploadORC() {
	if (attachCnt < attachId.size()) {
		changeStatus = "ORCATTACH";
		rId = attachId[attachCnt];
		Openfile.addAttachFileByFileName(attach_seq, $G("outAttachBox").cells(rId,"file_path").getValue(), 
											$G("outAttachBox").cells(rId, "doc_seq").getValue());
	} else {
		alert($L('SN_I023'));
		PopWin.setStatus('OutReqChangeDiv', $L("SN_I119", "작업이 완료되었습니다."));
		PopWin.hide('OutReqChangeDiv');
		TempFolderDelete();
	}
}


function doReViewDwg() {
	var selectedId;
	var doc_seq1;
	var doc_seq2;
	
	if(buttonCheck == 1){
		
		selectedId = mygrid.getSelectedRowId();
		
		if (selectedId == null || selectedId == "") {
			alert($L('SN_D233'));
			return;
		}
		selectedId = $w(selectedId.gsub(',', ' '));

		var compare1;
		var compare2;
		if (selectedId.size() != 2) {
			alert("비교할 도면을 2개 선택하여 주시기 바랍니다.");
			return;
		} 

		if (Number($G('gridbox').cells(selectedId[0], 'rev_no').getValue()) > Number($G(
				'gridbox').cells(selectedId[1], 'rev_no').getValue())) {
			doc_seq1 = $G('gridbox').cells(selectedId[0], 'doc_seq').getValue();
			doc_seq2 = $G('gridbox').cells(selectedId[1], 'doc_seq').getValue();
		} else {
			doc_seq1 = $G('gridbox').cells(selectedId[1], 'doc_seq').getValue();
			doc_seq2 = $G('gridbox').cells(selectedId[0], 'doc_seq').getValue();
		}

	}else{
		/**
		 * 2020.12.08(화)
		 * function : doReViewDwg()
		 * DataView로 인한 기능 분기
		 */
		selectedId = myDataView.getSelected();
		
		if (selectedId == null || selectedId == "") {
			alert($L('SN_D233'));
			return;
		}

		var compare1;
		var compare2;
		
		try {
			// 다수 선택일 경우
			if (selectedId.size() != 2) {
				alert("비교할 도면을 2개 선택하여 주시기 바랍니다.");
				return;
			}
		} catch (e) {
			// 한개 션택일 경우
			alert("비교할 도면을 2개 선택하여 주시기 바랍니다.");
			return;
		}
		 

		if (Number(myDataView.get(selectedId[0]).rev_no) > Number(myDataView.get(selectedId[1]).rev_no)) {
			doc_seq1 = myDataView.get(selectedId[0]).doc_seq;
			doc_seq2 = myDataView.get(selectedId[1]).doc_seq;
		} else {
			doc_seq1 = myDataView.get(selectedId[1]).doc_seq;
			doc_seq2 = myDataView.get(selectedId[0]).doc_seq;
		}
		
	}
		
	
	var param = "?socketDTO.f_category_id=" + doc_seq1;
	var url = _contextPath + "/edms/socket/seDocumentOpen.action";
	new Ajax.Request(url, {
		method : 'POST',
		parameters : param.toQueryParams(),
		asynchronous : false,
		onSuccess : function(response) {
			compare1 = eval('(' + response.responseText + ')');
		}
	});

	var param = "?socketDTO.f_category_id=" + doc_seq2;
	var url = _contextPath + "/edms/socket/seDocumentOpen.action";
	new Ajax.Request(url, {
		method : 'POST',
		parameters : param.toQueryParams(),
		asynchronous : false,
		onSuccess : function(response) {
			compare2 = eval('(' + response.responseText + ')');
		}
	});

	if (compare1.org_file.substring(compare1.org_file.lastIndexOf(".") + 1)
			.toLowerCase() == "dwg"
			&& compare2.org_file.substring(
					compare2.org_file.lastIndexOf(".") + 1).toLowerCase() == "dwg") {
		DocumentCompare(compare1, compare2);
	} else {
		alert("해당 기능은 DWG 확장자만 사용하실 수 있습니다.");
		return;
	}

}



function doReqChangeDivAttachList(row_id)
{
	changeStatus = "";
	var rowIds = row_id;
	
	if (rowIds == null) {
		alert($L("SN_D233"));
		return;
	}
//	rowIds = $w(rowIds.gsub(',', ' '));
//	if(rowIds.size() > 1){
//		alert("파일 1개만 선택가능합니다.");
//		return;
//	}

	PopWin.w('ReqChangeDiv').setModal(false);
	PopWin.w('ReqChangeDivAttachList').setModal(true);
	PopWin.w('ReqChangeDivAttachList').show();
	
}

function doReqChangeDivAttachListSave()
{
	PopWin.w('ReqChangeDivAttachList').setModal(false);
	PopWin.w('ReqChangeDivAttachList').hide();
}
function doReqChangeDivAttachListAdd()
{
	FileSelect();
}
function CallBackAddRow(data) {
	
	files = data.files;
	if (files == null)
		return;
	
	var i=0;
	
	var isExist = (attachFiles.indexOf($G('ReqChangegridbox').cells(RCgrid.getSelectedRowId(), 'doc_seq').getValue())!== -1);
	if(isExist == false)
	{
		attachFiles.push($G('ReqChangegridbox').cells(RCgrid.getSelectedRowId(), 'doc_seq').getValue());
	}
	files.forEach(function(id){
		var row = $G('attachBox').defValue;
		var newID = attachgridBox.uid();
		row.set('seq', i);
		row.set('attach_name', $G('ReqChangegridbox').cells(RCgrid.getSelectedRowId(), 'doc_no').getValue());
		row.set('file_path', files[i].localpath);
		row.set('doc_seq', $G('ReqChangegridbox').cells(RCgrid.getSelectedRowId(), 'doc_seq').getValue());
		$G('attachBox').addRow(newID, row);
		console.log(files[i].localpath);
		i++;
	})
	$G('ReqChangegridbox').cells(RCgrid.getSelectedRowId(), 'attach_yn').setValue("Y");

}
function doReqChangeDivAttachListDelete()
{
	attachgridBox.deleteSelectedRows();

	var allId = attachgridBox.getAllRowIds();

	attachFiles.forEach(function(id) {
		$G('ReqChangegridbox').cells(id, 'attach_yn').setValue("N");
	})
	allId = $w(allId.gsub(',', ' '));
	attachFiles = [];
	if (allId != "") {
		allId.forEach(function(id) {
			var isExist = (attachFiles.indexOf($G('ReqChangegridbox').cells(
					$G("attachBox").cells(id, "doc_seq").getValue(), 'doc_seq')
					.getValue()) !== -1);
			if (isExist == false) {
				attachFiles.push($G('ReqChangegridbox').cells(
						$G("attachBox").cells(id, "doc_seq").getValue(),
						'doc_seq').getValue());
				$G('ReqChangegridbox').cells($G("attachBox").cells(id, "doc_seq").getValue(), 'attach_yn').setValue("Y");
			}
		})
	}
}

function doTagInfoDivExcel() {
	PopWin.w('tagInfoDiv').setModal(false);
	j$('#fmTempuserFileUpload')[0].reset();
	PopWin.show("fileUploadDiv");
}

function checkExt2() {
	var EX_FORMAT = "\.(xls|xlsx)$";

	if (!(new RegExp(EX_FORMAT, "i")).test(j$('#fileSelecter2').val())) {
		alert($L('W_IS_NOT_EXCELFILE'));
		return false;
	}

	var fileName = j$('#fileSelecter2').val();
	fileName = fileName.substring(fileName.lastIndexOf("\\") + 1,
			fileName.length);

	return true;
}
// end

// 추가 버튼 이벤트
function doTagInfoDivAdd() {
	// alert("추가");
	var row = $G('tagInfogridbox').defValue;
	var newID = $G('tagInfogridbox').newID();
	row.set('check', 1);
	row.set('doc_seq', $('docInforFormDTO_documentDTO_doc_seq').value);
	$G('tagInfogridbox').addRow(newID, row);
}

// 저장 버튼 이벤트
function doTagInfoDivSave() {
	
	 swal({
			title : '',
			text : $L("SN_C017"),
			type : 'info',
			showCancelButton : true,
			confirmButtonClass : "btn-danger",
			confirmButtonText : "예",
			cancelButtonText : "아니오",
			closeOnConfirm : true,
			closeOnCancel : true
		}, function(isConfirm) {
			if (isConfirm) {
				selectedId = tagInfoGrid.getCheckedRows(1); // mygrid.(1);
				if (selectedId == null || selectedId == "") {
					this.closeOnConfirm = false;
					alert($L('SN_D233'));
					return;
				}

				var nEmptyCell = $G("tagInfogridbox").checkNulls();
				if (nEmptyCell != -1) {
					this.closeOnConfirm = false;
					alert($G("tagInfogridbox").init.columns[nEmptyCell].label + ":"
							+ $L('SN_D092'));
					return;
				}

				$G('tagInfogridbox').doSaveAjaxCall(
						_contextPath + "/edms/dc/saveTagInfo.action", function() {
							searchTagInfo();
						});

				changeTagText(); // 자료정보 Tag text 변경
			}else{
				return;
			}

		});
}

// 삭제 버튼 이벤트
function doTagInfoDivDelete() {
	// alert("삭제");

	var checked = tagInfoGrid.getCheckedRows(0); // mygrid.getCheckedRows(1);
	var rows = $w(checked.gsub(',', ' '));
	if (rows == null || rows == "") {
		alert($L('SN_D233'));
		return;
	}
	
	 swal({
			title : '',
			text : $L("SN_C014"),
			type : 'info',
			showCancelButton : true,
			confirmButtonClass : "btn-danger",
			confirmButtonText : "예",
			cancelButtonText : "아니오",
			closeOnConfirm : false,
			closeOnCancel : true
		}, function(isConfirm) {
			if (isConfirm) {
				rows.each(function(rId) {
					if (rId.startsWith('_N')) {
						tagInfoGrid.deleteRow(rId);
						return;
					}

					var doc_seq = $G('tagInfogridbox').cells(rId, 'doc_seq').getValue();
					var oldItem = $G('tagInfogridbox').cells(rId, 'oldItem').getValue();

					var url = _contextPath + "/edms/dc/deTagInfo.action";
					var param = "itemDTO.doc_seq=" + doc_seq + "&itemDTO.oldItem="
							+ oldItem + "&itemDTO.item=" + oldItem;
					new Ajax.Request(url, {
						method : 'POST',
						parameters : param,
						asynchronous : false,
						onSuccess : function(response) {
							if (response.responseText.indexOf('Error!!') > -1) {
								alert($L("SN_I057"));
								return;
							}
							// 삭제 완료후 진행
							tagInfoGrid.deleteRow(rId); // row 삭제
							changeTagText(); // 자료정보 Tag 항목 Text 변경
							alert($L("SN_I029"));
						}
					});
				});
			}else{
				return;
			}

		});
	
}

// 자료정보 팝업의 Tag 항목 text 변경 메서드
function changeTagText() {
	var text = "";
	var cnt = tagInfoGrid.getRowsNum();
	for (var i = 0; i < cnt; i++) {
		var rId = tagInfoGrid.getRowId(i);
		text += $G('tagInfogridbox').cells(rId, 'item').getValue();
		if (i != cnt - 1)
			text += ",";
	}

	$('docInforFormDTO_documentDTO_tagList').value = text;
}

function sharedInit() {
	var init = {
		parent : 'sharedTreeBox',
		image_path : _contextPathURL + "/dhtmlxSuite/skins/web/imgs/dhxtree_web/",
		firstURL : _contextPath + '/edms/tr/seGetRoleLazeLoadShared.action',
		width : "100%",
		height : "100%",
		insertItem : {
			parentid : 'pid',
			id : 'tid',
			name : 'tname'
		}
	};
	sharedTree = makeAttachTree(tab.cells("c"), init);
	sharedTree.enableAutoTooltips(true);
}

function doSharedAdd()
{
	
	if(buttonCheck == 1){
		
		var rowIds = mygrid.getSelectedRowId();
		if (rowIds == null) {
			alert($L('SN_D233'));
			return;
		}
		rowIds = $w(rowIds.gsub(',', ' '));
		
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doSharedAdd()
		 * DataView로 인한 기능 분기
		 */
		var rowIds = myDataView.getSelected();
		if (rowIds == null) {
			alert($L('SN_D233'));
			return;
		}
		
		try {
			// 다수 선택일 경우
			var t = rowIds.size();
			rowIds = rowIds.join();
		} catch (e) {
			// 한개 션택일 경우
			rowIds = rowIds;
		}
		
		
	}
	
	var emp_id = _ses.ses_emp_id;
	url = _contextPath + "/edms/dc/SharedAddBag.action?";
	var param = "searchFormDTO.documentDTO.doc_seq_list=" + rowIds;

	new Ajax.Request(url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) {
			if ('success' == ('' + response.responseText)) {
				//alert($L('SN_I038', '성공적으로 저장되었습니다.'));
				var gridQString = _contextPath + "/edms/dc/seAddSharedList.action?";
				sharedgrid.clearAndLoad(gridQString+"&searchFormDTO.gridNames="+$G('sharedgridbox').getIds()+"&searchFormDTO.emp_id="+_ses.ses_emp_id,null		
				);
				PopWin.show('SharedListDiv');
			} else {
				alert($L('SN_E081', '작업을 실패했습니다.'));
			}
		}
	});
}

function sharedAdd(){
	var rowIds = sharedgrid.getAllRowIds();
	var shared_id = j$("#shared_id").val();
	if (rowIds == null || rowIds == '') {
		alert($L('SN_D233'));
		return;
	}
	if(shared_id == null || shared_id == ""){
		alert($L('SN_D286'));
		return;
	}
	rowIds = $w(rowIds.gsub(',', ' '));
	
	url = _contextPath + "/edms/dc/inShareFolderDoc.action?";
	var param = "searchFormDTO.shareFolderDTO.doc_seq_list=" + rowIds + "&searchFormDTO.shareFolderDTO.share_add_id=" + _ses.ses_emp_id
				+ "&searchFormDTO.shareFolderDTO.share_seq=" + shared_id;

	new Ajax.Request(url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) {
			if ('SUCCESS' == (response.responseText)) {
				alert($L('SN_I038', '성공적으로 저장되었습니다.'));
			} else {
				alert($L('SN_E081', '작업을 실패했습니다.'));
			}
		}
	});
	PopWin.hide('SharedListDiv');
	
}

function sharedListDel(){
	var rowIds = sharedgrid.getSelectedRowId();
	if (rowIds == null || rowIds == '') {
		alert($L('SN_D233'));
		return;
	}
	rowIds = $w(rowIds.gsub(',', ' '));
	
	url = _contextPath + "/edms/dc/deShareListDel.action?";
	var param = "searchFormDTO.shareFolderDTO.doc_seq_list=" + rowIds + "&searchFormDTO.shareFolderDTO.share_add_id=" + _ses.ses_emp_id;

	new Ajax.Request(url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) {
			if ('SUCCESS' == (response.responseText)) {
				alert($L('SN_I029', '성공적으로 삭제되었습니다.'));
				for(var i=0; i<rowIds.size(); i++){
					sharedgrid.deleteRow(rowIds[i]);
				}
			} else {
				alert($L('SN_E081', '작업을 실패했습니다.'));
			}
		}
	});
}

function sharedTreeBoxonSelect(id) {

	gridQString = _contextPath + "/edms/dc/seShareDocSearch.action?";
	var param = "searchFormDTO.shareFolderDTO.share_seq="+id+"&searchFormDTO.shareFolderDTO.share_add_id="+_ses.ses_emp_id;

	mygrid.enablePaging(true,seSearchCount("SC"),10,"page",true,"infoArea");
	mygrid.setPagingSkin("bricks")

	mygrid.clearAndLoad(gridQString + param + "&searchFormDTO.gridNames="+ $G('gridbox').getIds(), function() {
		waiter.hide();
	});

}

function sharedTreeBoxonDblClick(id) {
	sharedTreeBoxonSelect(id);
}

function doSharedDel(){
	
	var rId;
	var share_seq
	var doc_seq_list = "";
	if(buttonCheck == 1){
		rId = mygrid.getSelectedRowId();
		if(rId == null || rId == ''){
			alert($L('SN_D233'));
			return;
		}
		rId = $w(rId.gsub(',', ' '));
		var share_seq = $G('gridbox').cells(rId[0],'share_seq').getValue();
		if(share_seq == null || share_seq == '' || share_seq == 0){
			alert($L('SN_SHARE_DOC_CHECK'));
			return;
		}
		
		for(var i=0; i<rId.size(); i++){
			if(i==0) doc_seq_list = $G('gridbox').cells(rId[i],'doc_seq').getValue();
			else{
				doc_seq_list = doc_seq_list + "," + $G('gridbox').cells(rId[i],'doc_seq').getValue();
			}
		}
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doSharedDel()
		 * DataView로 인한 기능 분기
		 */
		rId = myDataView.getSelected();
		
		if(rId == null || rId == ''){
			alert($L('SN_D233'));
			return;
		}
		
		try {
			// 다수 선택일 경우
			var t = rId.size();
			share_seq = myDataView.get(rId[0]).share_seq;
			if(share_seq == null || share_seq == '' || share_seq == 0){
				alert($L('SN_SHARE_DOC_CHECK'));
				return;
			}
			
			for(var i=0; i<rId.size(); i++){
				var data = myDataView.get(rId[i]);
				
				if(i==0) doc_seq_list = data.doc_seq;
				else{
					doc_seq_list = doc_seq_list + "," + data.doc_seq;
				}
			}
		} catch (e) {
			// 한개선택일 경우
			share_seq = myDataView.get(rId).share_seq;
			if(share_seq == null || share_seq == '' || share_seq == 0){
				alert($L('SN_SHARE_DOC_CHECK'));
				return;
			}
			
			var data = myDataView.get(rId);
			
			doc_seq_list = data.doc_seq;
			
		}
		
	}
	
	
	url = _contextPath + "/edms/dc/deShareDocDel.action?";
	var param = "searchFormDTO.shareFolderDTO.doc_seq_list=" + doc_seq_list + "&searchFormDTO.shareFolderDTO.share_seq=" + share_seq;

	new Ajax.Request(url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) {
			if ('SUCCESS' == (response.responseText)) {
				alert($L('SN_I029', '성공적으로 삭제되었습니다.'));
				rId.forEach(function(id) {
					
					if(buttonCheck == 1){
						mygrid.deleteRow(id);
					}else{
						myDataView.remove(id);
					}
					
				});
			} else {
				alert($L('SN_E081', '작업을 실패했습니다.'));
			}
		}
	});

}

var genAttrArray = new Array();
var dwgAttrArray = new Array();
genAttrArray = ['doc_no','doc_name','drawing_no','grade_id','completion_date','manager','design_vendor','construction_vendor','project_name','construction_no'];
//dwgAttrArray = ['doc_no','doc_name'];
genAttrTextArray = ['자료번호','자료명','도면번호','자료등급','준공일','담당자','설계사','시공사','프로젝트명','공사번호'];
function doAttrChange(){
	var rId ;
	attrchangeOptions = "";
	var selectSize;
	if(buttonCheck == 1){
		rId= mygrid.getSelectedRowId();
		if(rId == null || rId == ''){
			alert($L('SN_D233'));
			return;
		}
		selectSize = rId.split(',').size();
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doAttrChange()
		 * DataView로 인한 기능 분기
		 */
		rId= myDataView.getSelected();
		if(rId == null || rId == ''){
			alert($L('SN_D233'));
			return;
		}
		try{
			selectSize = rId.size();
		}catch (e) {
			selectSize = 1;
		}
	}
	
	var attrchangeSelect = document.getElementById('attrchange');
	attrchangeOptions = '<option value="">변경속성</option>';
	/*for(var i=0; i<genAttrArray.size(); i++){
		if(selectSize > 1 && genAttrArray[i] == 'doc_no'){
			attrchangeOptions += '<option value="'+ genAttrArray[i] +'" disabled>'+genAttrTextArray[i]+'</option>';
		}else{
			attrchangeOptions += '<option value="'+ genAttrArray[i] +'">'+genAttrTextArray[i]+'</option>';
		}	
	}*/
	
	var url = _contextPath + "/edms/dc/seAttrChangeList.action";
	var param = "?emp_id=" + _ses.ses_emp_id;
	new Ajax.Request(url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) {
			var json = eval('(' + response.responseText + ')');
			for(var i=0; i<json.size(); i++){
				attrchangeOptions += '<option value="'+ json[i].id +'">'+json[i].label+'</option>';
			}
			attrchangeSelect.innerHTML = attrchangeOptions;
		}
	});

	j$('#change_text').val('');
	document.getElementById('BOOK_CATEGORY').value = "";
	PopWin.show("attrChangeDiv");
	PopWin.setStatus('attrChangeDiv',"");
}

function attrChangeSave(){
	if(buttonCheck == 1){
		rId= mygrid.getSelectedRowId();
		if(rId == null || rId == ''){
			alert($L('SN_D233'));
			return;
		}
	}else{
		/**
		 * 2020.12.08(화)
		 * function : attrChangeSave()
		 * DataView로 인한 기능 분기
		 */
		rId= myDataView.getSelected();
		if(rId == null || rId == ''){
			alert($L('SN_D233'));
			return;
		}
		
		try {
			// 다수 선택일 경우
			var t = rId.size();
			rId = rId.join(',');
		} catch (e) {
			// 한개 선택일 경우
			rId = rId;
		}
		
	}
	
	var attr_kind = j$('#attrchange').val();
	var change_text = "";
	if(attr_kind == 'grade_id'){
		change_text = j$('#grade_id').val();
	}
	else if (attr_kind=='update_date'){
		change_text = j$('#attrchange_date').val();
	}
	else if (attr_kind=='publicDoc'){
		change_text = j$('#publicDocYN').val();
		if(change_text == null || change_text == "" || change_text == "null"){
			change_text = "N";
		}	
	}
	else if (attr_kind=='book_category'){
		change_text = j$('#BOOK_CATEGORY').val();
	}
	else if(attr_kind == 'rev_no'){
		change_text = j$('#change_text').val();
		
		if(isInteger(change_text)){
			if(change_text.indexOf(".") >=0){
				alert("REV은 숫자만 입력가능합니다.");
				return;
			}
		}else{
			alert("REV은 숫자만 입력가능합니다.");
			return;
		}
		j$("#attrAllRev").prop("checked",false);
		j$('#attrAll').val("cn");
	}
	else {
		change_text = j$('#change_text').val();
	}

	if(attr_kind == null || attr_kind == '' ){
		alert("변경하고자 하는 값을 선택해 주시기 바랍니다.")
		return;
	}

	url = _contextPath + "/edms/dc/attrChange.action?";
	var param = "documentDTO.doc_seq_list=" + rId 
		+ "&documentDTO.attr_kind="+attr_kind 
		+ "&documentDTO.change_text="+change_text
		+ "&documentDTO.attrAll="+j$('#attrAll').val();
	
	if(attr_kind == 'doc_no'){
		param += "&documentDTO.tree_id="+$G('gridbox').cells(rId,'tree_id').getValue();
	}
	

	new Ajax.Request(url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) {
	
			if ('SUCCESS' == (response.responseText)) {
				
				if(buttonCheck == 1){
					rId = $w(rId.gsub(',', ' '));
					var old_doc_no = "";
					if(attr_kind == "doc_no"){
						old_doc_no = $G('gridbox').cells(rId[0],attr_kind).getValue();
					}
					for(var i=0; i<rId.size(); i++){
						
						$G('gridbox').cells(rId[i],attr_kind).setValue(change_text);
						
						if(j$('#attrAll').val() == 'ck'){
							var changeDocNo = $G('gridbox').cells(rId[i],'doc_no').getValue();
							var changeTreeId = $G('gridbox').cells(rId[i],'tree_id').getValue();
							
							mygrid.forEachRow(function(id) {
								
								/*if($G('gridbox').cells(id, 'doc_no').getValue() == changeDocNo
										&& $G('gridbox').cells(id, 'tree_id').getValue() == changeTreeId){
									$G('gridbox').cells(id,attr_kind).setValue(change_text);
								}*/
								if(attr_kind == "doc_no"){
									if($G('gridbox').cells(id, 'doc_no').getValue() == old_doc_no){
										$G('gridbox').cells(id,attr_kind).setValue(change_text);
									}
								}else{
									if($G('gridbox').cells(id, 'doc_no').getValue() == changeDocNo){
										$G('gridbox').cells(id,attr_kind).setValue(change_text);
									}
								}
							});
							
						}
					}
				}/*else{
						doSearch();
				}*/
					//속성 변경 후 이력 저장
						param = "historyFormDTO.documentDTO.doc_seq_list=" + rId +
					    "&historyFormDTO.workhistoryDTO.kind=DU" +
					    "&historyFormDTO.documentDTO.commts=문서정보 수정";
				
				
					var Url = _contextPath + "/edms/ht/inWorkHistory.action?";
					new Ajax.Request(Url, {
					    method: 'POST',
					    parameters: param,
					    asynchronous: false,
					    onSuccess: function(response) {
							var attrTarget = document.getElementById("attrchange");
							attrTarget.options[attrTarget.selectedIndex].text
							PopWin.setStatus('attrChangeDiv', attrTarget.options[attrTarget.selectedIndex].text
									+ "의 값이 " + change_text + "으로 변경되었습니다.");
					    }
					});
				//end
				
			} else {
				
				alert(response.responseText);
				PopWin.setStatus('attrChangeDiv', "");
				
			}
			
			
			
		}
	});
	
}

function attrChangeCancel(){
	PopWin.hide("attrChangeDiv");
}

function preview(doc_seq){
	var param = "?socketDTO.f_category_id=" + escape(doc_seq)+"&socketDTO.sftp="+_ses.sftp;
	var url = _contextPath + "/edms/socket/seDocumentOpen.action";
	new Ajax.Request(url, {
		method : 'POST',
		parameters : param.toQueryParams(),
		asynchronous : false,
		onSuccess : function(response) {
			DocumentOpen(response.responseText);
		}
	});
}

function doCreateTree(id){
	$('contextTreeId').value = id;
	
	PopWin.show('createTreeDiv');
}

function createTree(id){
	var id = $('contextTreeId').value;
	
	var targetInfo = new Array();
	targetInfo[0] = $('targetName').value;
	targetInfo[1] = $('targetCode').value;
	
	if(targetInfo[0].length < 1 
			|| targetInfo[0] == null 
			|| targetInfo[0] == '' 
			|| targetInfo[0] == 'undefined')
	{
		alert("폴더명을 입력하지 않았습니다.");
		return false;
	}
	
//	if(targetInfo[1].length < 1 
//			|| targetInfo[1] == null 
//			|| targetInfo[1] == '' 
//			|| targetInfo[1] == 'undefined')
//	{
//		alert("코드명을 입력하지 않았습니다.");
//		return false;
//	}
	
	var targetText = targetInfo[0];
	var targetCode = targetInfo[1];
		
	var param = "mstrTreeFormDTO.targetId=" + id + "&mstrTreeFormDTO.targetCategory=T&mstrTreeFormDTO.targetText="+targetText + "&mstrTreeFormDTO.targetCode="+targetCode;

	var url = _contextPath + "/edms/tr/searchCreateTree.action?";
	new Ajax.Request(url, {
		method : 'POST',
		parameters : param,
		asynchronous : false,
		onSuccess : function(response) 
		{
			debugger;
			if(response.responseText.indexOf('Error!!') < 0)
			{
				
				var new_tid = response.responseText;
				
				mytree.insertNewChild(id,new_tid,"("+targetCode+") "+targetText);
				
				PopWin.hide('createTreeDiv');
				
				debugger;
				//main_tabbar.setTabActive(id)
				
				
			}
			else
			{
				alert("폴더 생성에 실패하였습니다.")
			}
			
		}
	});
	
}

function removeTree(id){
	
	 swal({
			title : '',
			text : "해당 폴더를 삭제하시겠습니까?",
			type : 'info',
			showCancelButton : true,
			confirmButtonClass : "btn-danger",
			confirmButtonText : "예",
			cancelButtonText : "아니오",
			closeOnConfirm : false,
			closeOnCancel : true
		}, function(isConfirm) {
			if (isConfirm) {
				/**
				 * 삭제 시 해당분류에 문서가 포함되어 있으면 삭제 할 수 없습니다.
				 */
				var param = "mstrTreeFormDTO.targetCategory=T&mstrTreeFormDTO.targetId=" + id;
				var url = _contextPath + "/edms/tr/searchRemoveTree.action?";
				new Ajax.Request(url, {
					method : 'POST',
					parameters : param,
					asynchronous : false,
					onSuccess : function(response) 
					{
						
						if(response.responseText == "SUCCESS")
						{
							mytree.deleteItem(id);
							alert("삭제되었습니다.");
						}
						else if(response.responseText == "EXTDOC")
						{
							alert("해당 분류내의 문서가 존재하여 삭제 할 수 없습니다.")
						}else{
							alert("삭제에 실패하였습니다.");
						}
					}
				});
			}else{
				return false;
			}

		});
	
}

function doOutDistributeReq(){
	var selectedId;
	var doc_seq_list="";
	
	if(buttonCheck == 1){
		selectedId = mygrid.getSelectedRowId();

		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		selectedId = $w(selectedId.gsub(',', ' '));
		
		for (var i = 0; i < selectedId.size(); i++) {
			var doc_seq = $G('gridbox').cells(selectedId[i],'doc_seq').getValue();
			if(i==0) doc_seq_list = doc_seq;
			else doc_seq_list = doc_seq_list + "," + doc_seq;
		}
	}else{
		/**
		 * 2020.12.08(화)
		 * function : doOutDistributeReq()
		 * DataView로 인한 기능 분기
		 */
		selectedId = myDataView.getSelected();

		if (selectedId == null) {
			alert($L('SN_D233'));
			return;
		}
		
		try {
			// 다수 선택일 경우
			for (var i = 0; i < selectedId.size(); i++) {
				var doc_seq = $G('gridbox').cells(selectedId[i],'doc_seq').getValue();
				if(i==0) doc_seq_list = doc_seq;
				else doc_seq_list = doc_seq_list + "," + doc_seq;
			}
		} catch (e) {
			// 한개 선택일 경우
			doc_seq_list = selectedId;
		}
		
		
	}
	
	
	var resultDupChk = true;
	if(new_tree_id == "M"){
		var url = _contextPath + "/edms/dc/seOutDistributeReqListDupChk.action?";	
		var param = "doc_seq_list="+doc_seq_list + "&emp_id="+_ses.ses_emp_id;
		
		new Ajax.Request(url, {
			method : 'POST',
			parameters : param,
			asynchronous : false,
			onSuccess : function(response) 
			{	
				if(response.responseText == "DUP")
				{
					resultDupChk = false;
				}
			}
		});
	}
	
	if(!resultDupChk){
		alert("중복된 문서가 포함되어 있습니다.");
		return;
	}
	
	gridQString = _contextPath + "/edms/dc/seOutDistributeReqList.action?doc_seq_list="+doc_seq_list + "&emp_id="+_ses.ses_emp_id;
	
	OutDistributeReqGrid.clearAndLoad(gridQString + "&distributeMngDTO.gridNames="+ $G('OutDistributeReqGridbox').getIds(), function() {
	});
	PopWin.show("OutDistributeReqDiv");
}

function eXcell_company(cell) {
	   if (cell) { 
	      this.cell = cell;
	      this.grid = this.cell.parentNode.grid;
	   }

	   this.edit = function() {
		   CompanyInfoSelect('G', this.grid.getSelectedRowId(), this.grid.cell_cellIndex);
	     // this.val = this.getValue(); 
	      //this.cell.innerHTML="<input type='text' value='"+this.val+"' style='width:80px;' disabled='true'><input type='button' value='...' style='width:40px;' onClick='CompanyInfoSelect();'>";
	   }; 

	   this.isDisabled = function() {
	      return false;
	   }; 
	   this.setValue = function(val) {
	      this.cell.innerHTML = "";
	      this.cell._val = val;
	      this.cell.appendChild(document.createTextNode(val));
	      this.grid.callEvent("onCellChanged", [ this.cell.parentNode.idd,
	            this.cell._cellIndex, val ]);
	      
	   };
	   this.getValue = function() {
	      return this.cell._val;
	   };
	   
	   this.detach = function() {
			this.setValue(this.cell._val);
			return this.val != this.getValue();
		};
}
eXcell_company.prototype = new eXcell;

/**
 * 요청관리 시 첨부파일 celltype 지정
 * @param cell
 * @returns
 */
function eXcell_RCfile(cell){ //the eXcell name is defined here
    if (cell){                // the default pattern, just copy it
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
//    cell.style.backgroundColor='gray';
    this.edit = function(){}  //read-only cell doesn't have edit method
    // the cell is read-only, so it's always in the disabled state
    this.isDisabled = function(){ return true; }
    this.setValue=function(val){
    	var attach_row_id = 'attach_file_'+this.cell.parentNode.idd;
    	
        this.setCValue("<div class='filebox'><input class='upload-name' value='파일선택' disabled='disabled'>" +
        				"<label class='input-file-button' for='"+attach_row_id+"'><img src='/img/ico/save.png'></label>" +
        				"<input type='file' id='"+attach_row_id+"' value='"+val+"' onchange='rcAtfileChange(this);' style='display:none'/>" +
        				"<div class='input-file-delete'><img  src='/img/ico/delete.png' onclick='rcAtfileDelete("+attach_row_id+");'></div></div>",val);
//        						                                      
    }
}
eXcell_RCfile.prototype = new eXcell;

/**
 * 요청관리 시 Markup celltype 지정
 * @param cell
 * @returns
 */
function eXcell_RCmarkup(cell){ //the eXcell name is defined here
    if (cell){                // the default pattern, just copy it
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
//    cell.style.backgroundColor='gray';
    this.edit = function(){}  //read-only cell doesn't have edit method
    // the cell is read-only, so it's always in the disabled state
    this.isDisabled = function(){ return true; }
    this.setValue=function(val){
    	var markup_row_id = 'markup_'+this.cell.parentNode.idd;
    	
    	if(val == 'Y'){
    		this.setCValue("<div class='filebox'><input id='"+markup_row_id+"' class='upload-name' value='' placeholder='마크업선택' disabled='disabled'>" +
    				"<img class='input-file-button' style='display:inline-block' src='/img/ico/markup_view.png' onclick='markupfileSelect("+markup_row_id+","+this.cell.parentNode.idd+")'>" +
    				"<div class='input-file-delete'><img  src='/img/ico/delete.png' onclick='markupfileDelete("+markup_row_id+");'></div></div>",val);
    	}else{
    		this.setCValue('');
    	}
        
//        						                                      
    }
}
eXcell_RCmarkup.prototype = new eXcell;

/**
 * 요청관리 시 Markup celltype 지정
 * @param cell
 * @returns
 */
function eXcell_mkpreview(cell){ //the eXcell name is defined here
	if (cell){                // the default pattern, just copy it
		this.cell = cell;
		this.grid = this.cell.parentNode.grid;
	}
//    cell.style.backgroundColor='gray';
	this.edit = function(){}  //read-only cell doesn't have edit method
	// the cell is read-only, so it's always in the disabled state
	this.isDisabled = function(){ return true; }
	this.setValue=function(val){
		var doc_seq = $G('markupgridbox').cells(this.cell.parentNode.idd,'doc_seq').getValue();
		var markupseq = $G('markupgridbox').cells(this.cell.parentNode.idd,'markupseq').getValue();
		
		this.setCValue("<input type='button' value='미리보기' style='width:100px; height:20px;' class='btn' onclick='markupPreview("+doc_seq+","+markupseq+")'>");
	};
}
eXcell_mkpreview.prototype = new eXcell;

function OutDistributeReq(){
	var rows = OutDistributeReqGrid.getAllRowIds();
	rows = $w(rows.gsub(',', ' '));
	
	var current = new Date();
	var year = current.getFullYear();
	var month = parseInt(current.getMonth() + 1) < 10 ? '0'
			+ (current.getMonth() + 1) : current.getMonth() + 1;
	var day = parseInt(current.getDate()) < 10 ? '0' + current.getDate(): current.getDate();
	current = year+"" + month+"" + day+"";
	
	for(var i=0; i<rows.size(); i++){
		var distribute_enddate = $G('OutDistributeReqGridbox').cells(rows[i],"distribute_enddate").getValue();
		if(distribute_enddate == null || distribute_enddate == ''){
			OutDistributeReqGrid.setSelectedRow(rows[i]);
			alert("만료일자 입력바랍니다.");
			return;
		}else{
			var selectedDate = distribute_enddate.replace(/-/gi, '');
			var diff = parseInt(selectedDate) - parseInt(current);
			if (diff < 0) {
				OutDistributeReqGrid.setSelectedRow(rows[i]);
				alert($L('SN_ENTERING_DUE_INPUT') + " (" + year + "년 " + month + "월 " + day + "일 이후)");
				return;
			}
		}
		
		var down_cnt = $G('OutDistributeReqGridbox').cells(rows[i],"download_cnt").getValue();
		if(down_cnt == null || down_cnt ==""){
			OutDistributeReqGrid.setSelectedRow(rows[i]);
			alert("다운로드 횟수 입력바랍니다.");
			return;
		}else{
			if(isNaN(down_cnt)){
				OutDistributeReqGrid.setSelectedRow(rows[i]);
				alert("숫자형식이 아닙니다.");
				return;
			}
		}
		
		var print_cnt = $G('OutDistributeReqGridbox').cells(rows[i],"print_cnt").getValue();
		if(print_cnt == null || print_cnt ==""){
			OutDistributeReqGrid.setSelectedRow(rows[i]);
			alert("출력 횟수 입력바랍니다.");
			return;
		}else{
			if(isNaN(print_cnt)){
				OutDistributeReqGrid.setSelectedRow(rows[i]);
				alert("숫자형식이 아닙니다.");
				return;
			}
		}
		
		var company_id = $G('OutDistributeReqGridbox').cells(rows[i],"company_id").getValue();
		if(company_id == null || company_id ==""){
			OutDistributeReqGrid.setSelectedRow(rows[i]);
			alert("업체명 입력바랍니다.");
			return;
		}	
	}
	var result;
	for(var i=0; i<rows.size(); i++){
		var param = $G("OutDistributeReqGridbox").getParamsDTORow(rows[i]);
		var url = _contextPath + "/edms/site/hanwha/dc/inDistributeDocList.action";
		new Ajax.Request(url, {
			method : "POST",
			parameters : param,
			asynchronous : false,
			onSuccess : function(response) {
				if (response.responseText == "SUCCESS") {
					result=true;
				}else{
					result=false;
					return;
				} 
			}
		});
	}
	if(result){
		alert("배포가 완료되었습니다.");
		PopWin.hide("OutDistributeReqDiv");
		return;
	}else{
		alert("배포 중 오류가 발생되었습니다.");
		return;
	}
	
	
}

function OutDistributeReqCancel(){
	PopWin.hide("OutDistributeReqDiv");
}


function OutDistributeAllChange(type){
	var value="";
	var value2 = "";
	if(type=="company"){
		value = $("OutDistributeReqFrm_distributeMngDTO_company_id").value;
		value2 = $("OutDistributeReqFrm_distributeMngDTO_company_name").value; 
		if(value == "" || value == null){
			alert("값을 입력바랍니다.");
			return;
		}
	}
	else if(type=="date"){
		value = j$("#distribute_enddate").val();
		if(value == "" || value == null){
			alert("값을 입력바랍니다.");
			return;
		}
		var current = new Date();
		$("OutDistributeReqFrm_distributeMngDTO_distribute_enddate").value = j$("#distribute_enddate").val();
		var selectedDate = $("OutDistributeReqFrm_distributeMngDTO_distribute_enddate").value.replace(/-/gi, '');
		var year = current.getFullYear();
		var month = parseInt(current.getMonth() + 1) < 10 ? '0'
				+ (current.getMonth() + 1) : current.getMonth() + 1;
		var day = parseInt(current.getDate()) < 10 ? '0' + current.getDate()
				: current.getDate();
		current = year+"" + month+"" + day+"";
		var diff = parseInt(selectedDate) - parseInt(current);

		if (diff < 0) {
			alert($L('SN_ENTERING_DUE_INPUT') + " (" + year + "년 " + month + "월 " + day + "일 이후)");
			return;
		}
		
	}
	else if(type=="down"){
		value = $("OutDistributeReqFrm_distributeMngDTO_download_cnt").value;
		if(value == "" || value == null){
			alert("값을 입력바랍니다.");
			return;
		}
	}
	else if(type=="print"){
		value = $("OutDistributeReqFrm_distributeMngDTO_print_cnt").value;
		if(value == "" || value == null){
			alert("값을 입력바랍니다.");
			return;
		}
	}
	
	OutDistributeReqGrid.forEachRow(function(id){
		if(type=="company"){
			$G('OutDistributeReqGridbox').cells(id,"company_id").setValue(value);
			$G('OutDistributeReqGridbox').cells(id,"company_name").setValue(value2);
		}else if(type=="date"){
			$G('OutDistributeReqGridbox').cells(id,"distribute_enddate").setValue(value);
		}else if(type=="down"){
			$G('OutDistributeReqGridbox').cells(id,"download_cnt").setValue(value);
		}else if(type=="print"){
			$G('OutDistributeReqGridbox').cells(id,"print_cnt").setValue(value);
		}
	});
}

function CompanyInfoSelect(type, pathId, pathName){
	gridQString = _contextPath + "/edms/dc/seCompanyInfoSelect.action?emp_id="+_ses.ses_emp_id;
	CompantInfoSelectInfoGrid.clearAndLoad(gridQString + "&searchFormDTO.gridNames="+ $G('CompantInfoSelectInfogridbox').getIds(), function() {
		CompantInfoSelectInfoGrid.forEachRow(function(id){
			$G('CompantInfoSelectInfogridbox').cells(id,"type").setValue(type);
			$G('CompantInfoSelectInfogridbox').cells(id,"pathId").setValue(pathId);
			$G('CompantInfoSelectInfogridbox').cells(id,"pathName").setValue(pathName);
		});
	});
	PopWin.show("CompantInfoSelectInfoDiv");
};

function CompantInfoSelectInfogridboxonRowDblClicked(rId, cInd) {
	var type = $G('CompantInfoSelectInfogridbox').cells(rId,"type").getValue();
	if(type == 'T'){
		var company_id = $G('CompantInfoSelectInfogridbox').cells(rId,"documentDTO.company_id").getValue();
		var company_name = $G('CompantInfoSelectInfogridbox').cells(rId,"documentDTO.company_name").getValue();
		var pathId = $G('CompantInfoSelectInfogridbox').cells(rId,"pathId").getValue();
		var pathName = $G('CompantInfoSelectInfogridbox').cells(rId,"pathName").getValue();
		
		$(pathId).value = company_id;
		$(pathName).value = company_name;
	}
	else if(type == 'G'){
		var company_id = $G('CompantInfoSelectInfogridbox').cells(rId,"documentDTO.company_id").getValue();
		var company_name = $G('CompantInfoSelectInfogridbox').cells(rId,"documentDTO.company_name").getValue();
		var pathId = $G('CompantInfoSelectInfogridbox').cells(rId,"pathId").getValue();
		
		$G('OutDistributeReqGridbox').cells(pathId,"company_id").setValue(company_id);
		$G('OutDistributeReqGridbox').cells(pathId,"company_name").setValue(company_name);
	}
	
	PopWin.hide("CompantInfoSelectInfoDiv");
}


function addSearchDataList(str) {
    if (str == undefined)
        str = "";

    var opt = "";
    var url = _contextPath + "/edms/dc/getRecentSearchWord.action?"; // 2014.2.14 수정
    var param = "emp_id=" + _ses.ses_emp_id + "&page_uri=" + _URI + "&word=" + str;
    new Ajax.Request(
        url, {
            method: 'POST',
            parameters: param,
            asynchronous: true,
            onSuccess: function(response) {
                var json = JSON.parse(response.responseText);
                const menu = document.getElementById("dropdown");
                for (var i = 0; i < json.size(); i++) {
					let word = json[i].word;
                    opt += '<li id="' + word + '"><p style="border: 1px solid transparent; box-sizing: border-box;">' + word + '<span style="float:right;">' + json[i].search_date + '. <button id="' + word + '" name="historyDelete">삭제</button></span></p></li>';
                }
                document.getElementById('dropdown').innerHTML = opt;
            }
        });

    let autoSaveYN = seMyPreference('SA');
    if (autoSaveYN == "true") {
        j$('#autoSaveOn').css("display", "none");
        j$('#autoSaveOff').css("display", "block");
    } else {
        j$('#autoSaveOn').css("display", "block");
        j$('#autoSaveOff').css("display", "none");
    }
}

function addFavoriteList() {
    var opt = "";
    var url = _contextPath + "/edms/ps/seFavriteList.action?";
    var param = "emp_id=" + _ses.ses_emp_id;
    new Ajax.Request(
        url, {
            method: 'POST',
            parameters: param,
            asynchronous: true,
            onSuccess: function(response) {
                var json = JSON.parse(response.responseText);
                const menu = document.getElementById("FavoriteDropdown");

                for (var i = 0; i < json.size(); i++) {
					let id = json[i].SORTNUM;
					let name = json[i].FAVORITE_NM;
                    opt += '<li id="' + id + '"><p id="' + id + '"style="border: 1px solid transparent; box-sizing: border-box;">' + name + '<span style="float:right;"> <button id="' + id + '" name="favoriteDelete">삭제</button></span></p></li>';
                }
                document.getElementById('FavoriteDropdown').innerHTML = opt;
            }
        });
}


function selectString(str, type,eid) {
	if(type == "M")
	{
    	document.getElementById("foreground_text_input").value = j$(str).text().replace(j$(str).find('span:eq(-1)').text(), "");
		j$('#rec_search_list').css("display", "none");
    	doSearch();
	}else if(type == "K")
	{
		 document.getElementById("foreground_text_input").value = str;
		 j$('#rec_search_list').css("display", "none");
    	 doSearch();
	}else if(type == "F")
	{
	let favorite_nm = j$(str).text().replace(j$(str).find('span:eq(-1)').text(), "");
		var url = _contextPath + "/edms/tr/seFavoriteInfo.action?";
		var param = "favoriteDTO.ses_emp_id=" + _ses.ses_emp_id + "&favoriteDTO.sort_num=" + eid;
		new Ajax.Request(
		    url, {
		        method: 'POST',
		        parameters: param,
		        asynchronous: true,
		        onSuccess: function(response) {
		            var json = eval('(' + response.responseText + ')');

					$('select_searchFormDTO_documentDTO_tree_id').value = json[0].tree_id;
					$('select_searchFormDTO_documentDTO_dockind_id').value = json[0].kind_id;
					
					document.getElementById("favorite_id").value = favorite_nm;
					doSearch();
		        }
		    });
		
		
		j$('#favorite_list').css("display", "none");
	}
}
function deleteSearchWord(value,type)
{
	if(type == "Search")
	{
		var url = _contextPath + "/edms/dc/deRecentSearchWord.action?";
		var param = "emp_id=" + _ses.ses_emp_id + "&page_uri=" + _URI + "&value=" + value;
		new Ajax.Request(
		    url, {
		        method: 'POST',
		        parameters: param,
		        asynchronous: true,
		        onSuccess: function(response) {
		            if (value != "") {
		                var parent = document.getElementById("dropdown");
		                var child = document.getElementById(value);
		                parent.removeChild(child);
		            }else
					{
		            	j$('#dropdown').empty();
					}
		        }
		    });
	}else if(type == "Favorite")
	{
		var url = _contextPath + "/edms/tr/deAllFavoriteList.action?";
		var param = "favoriteDTO.ses_emp_id=" + _ses.ses_emp_id+"&favoriteDTO.sort_num=" + value;
		new Ajax.Request(
		    url, {
		        method: 'POST',
		        parameters: param,
		        asynchronous: true,
		        onSuccess: function(response) {
		            if (value != "") {
		                var parent = document.getElementById("FavoriteDropdown");
		                var child = document.getElementById(value);
		                parent.removeChild(child);
		            }else
					{
		            	j$('#FavoriteDropdown').empty();
					}
					document.getElementById("favorite_id").value = "";
		        }
		    });	
	}	
}

function dndTreeChange(){
	dragCount=0;
	
	if($('upTreeAndKind_docInforFormDTO_documentDTO_chk_all_rev').value == 'ck'){
		
		
		$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq_list').value = sId_array.join(',');
		
		var url = _contextPath
		+ "/edms/dc/upTreeAndKind.action?docInforFormDTO.documentDTO.kind=tree"; // 2014.2.14 수정
		var param = Form.serialize('upTreeAndKind');
		new Ajax.Request(
				url,
				{
					method : 'POST',
					parameters : param,
					asynchronous : true,
					onSuccess : function(response) {
						if (Number(response.responseText) > 0) {
							alert('변경 되었습니다.');
							dragCount = 0;
							dragTotal = 0;
							dragFail = 0;
							//doSearch();
							PopWin.hide('DragTreeChangeDiv');
						}else{
							alert('동일한 분류 내의 같은 자료번호 중 잠겨있는 항목이 발견되어 이동할 수 없습니다.');
							PopWin.hide('DragTreeChangeDiv');
						}
					}
				}
			);
		
	}else{
		for(var i = 0; i < dragTotal; i++){
			
			if(dnd_obj == 'GR'){
				if($G("gridbox").cells(sId_array[i], "lock_status").getValue() == "T"){
					alert($G("gridbox").cells(sId_array[i], "doc_no").getValue() + " 문서는 잠겨있어 이동할 수 없습니다.");
					return false;
				}
			}else{
				
				if(myDataView.get(sId_array[i]).lock_status == "T"){
					alert(myDataView.get(sId_array[i]).doc_no + " 문서는 잠겨있어 이동할 수 없습니다.");
					return false;
				}
			}
			
			var docseqno = sId_array[i];
			
			var targetId = $('upTreeAndKind_docInforFormDTO_documentDTO_tree_id').value;
			var drawing_no = $G("gridbox").cells(sId_array[i], "drawing_no").getValue();
			var doc_nm = $G("gridbox").cells(sId_array[i], "doc_name").getValue();
			var ext = $G("gridbox").cells(sId_array[i], "ext").getValue();
			ext = ext.substring(0,ext.length-1);
			
			var treeDocNo = getTreeDocNo(targetId);
			$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq').value = docseqno;
			
			if(drawing_no == ''){
				$('upTreeAndKind_docInforFormDTO_documentDTO_doc_no').value = treeDocNo + doc_nm+ "."+ext;
			}else{
				$('upTreeAndKind_docInforFormDTO_documentDTO_doc_no').value = treeDocNo + drawing_no + "."+ext;
			}
			
			
			var url = _contextPath
			+ "/edms/dc/upTreeAndKind.action?docInforFormDTO.documentDTO.kind=tree"; // 2014.2.14 수정
			var param = Form.serialize('upTreeAndKind');
			new Ajax.Request(
					url,
					{
						method : 'POST',
						parameters : param,
						asynchronous : true,
						onSuccess : function(response) {
							if (Number(response.responseText) > 0) {
								/**
								 * IE의 경우 현재 Clear and Load 가 되고 있음
								 * 분류조회 방식이 현재 선택한 폴더의 내용만 보여줄 경우 deleteRow가 맞으나 하위분류 포함 조회가 된다면 clearAndLoad 방식으로 변경해야 함.
								 */
								/*분류 조회가 현재 폴더만 조회하는 경우*/
//								mygrid.deleteRow(sId);
								
								/*분류 조회가 하위 폴더까지 조회하는 경우*/
								dragCount++;
								
								if(dragCount == (dragTotal - dragFail)){
									alert('변경 되었습니다.');
									dragCount = 0;
									dragTotal = 0;
									dragFail = 0;
									doSearch();
									PopWin.hide('DragTreeChangeDiv');
								}
							}
						}
					}
				);
		}
	}
	
	
	
	
}

function dndTreeCancel(){
	PopWin.hide('DragTreeChangeDiv');
}

function dndMyTreeChange(){
	
	dragCount = 0;
	for(var i = 0; i < dragTotal; i++){
		/*분류 조회가 하위 폴더까지 조회하는 경우 End*/
		var docseqno = sId_array[i];
		
		$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq').value = docseqno;
		var url = _contextPath
		+ "/edms/dc/upMyTreeAndKind.action?docInforFormDTO.documentDTO.kind=tree"; // 2014.2.14 수정
		var param = Form.serialize('upTreeAndKind');
		new Ajax.Request(
				url,
				{
					method : 'POST',
					parameters : param,
					asynchronous : true,
					onSuccess : function(response) {
						if (Number(response.responseText) > 0) {
							dragCount++;
							
							if(dragCount == (dragTotal)){
								dragCount = 0;
								dragTotal = 0;
								alert('내문서함에 저장하였습니다.');
								mydoctree.clearSelection();
							}
						}
					}
				}
			);
	}
	
	
}

function dndMyTreeCreate(){
	
	/*분류 조회가 하위 폴더까지 조회하는 경우 End*/
	var docseqno = sId_array;
	var tId = $('upTreeAndKind_docInforFormDTO_documentDTO_tree_id').value;
		
	$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq').value = tId;
	$('upTreeAndKind_docInforFormDTO_documentDTO_doc_seq_z').value = docseqno;
	var url = _contextPath
	+ "/edms/dc/upMyTreeAndKindCreate.action?"; // 2014.2.14 수정
	var param = Form.serialize('upTreeAndKind');
	new Ajax.Request(
			url,
			{
				method : 'POST',
				parameters : param,
				asynchronous : true,
				onSuccess : function(response)
				{
					if(response.responseText.indexOf('Error!!') < 0)
					{
						var new_tid = response.responseText;
						var new_arr = new_tid.split(" ");
						mydoctree.insertNewChild(tId,new_arr[0],new_arr[1]);
						alert('폴더 복사에 성공하였습니다.');
						mydoctree.clearSelection();
						
					}
					else
					{
						alert("폴더 생성에 실패하였습니다.")
					}
				}
			}
	);
	
	
}

function createMyTree(id){
	
	//	var [targetText,targetCode] = prompt("추가할 폴더명과 코드를 입력해 주세요. ex)폴더명 코드").split(" ");
	
	swal({
		title : '',
			text : "생성할 폴더명을 입력해 주새요.",
			type : 'prompt',
			showCancelButton : true,
			inputType : "text",
			inputPlaceholder: "폴더명",
			inputValue: "",
			confirmButtonClass : "btn-danger",
			confirmButtonText : "예",
			cancelButtonText : "아니오",
			closeOnConfirm : false,
			closeOnCancel : true
	},function(result){
		if(result){
			if(result.length < 1 
					|| result == null 
					|| result == '' 
					|| result == 'ex)폴더명'
					|| result == 'undefined')
			{
				alert("폴더명을 입력하지 않았습니다.");
				return false;
			}
			
			var targetText = result;
				
			var param = "mstrTreeFormDTO.targetId=" + id + "&mstrTreeFormDTO.targetCategory=T&mstrTreeFormDTO.targetText="+encodeURIComponent(targetText);

			var url = _contextPath + "/edms/tr/searchCreateMyTree.action?";
			new Ajax.Request(url, {
				method : 'POST',
				parameters : param,
				asynchronous : false,
				onSuccess : function(response) 
				{
					debugger;
					if(response.responseText.indexOf('Error!!') < 0)
					{
						
						var new_tid = response.responseText;
						
						mydoctree.insertNewChild(id,new_tid,targetText);
						alert("폴더 생성에 성공하였습니다.")
						RealTimeIntegration("search");
						
					}
					else
					{
						alert("폴더 생성에 실패하였습니다.")
					}
					
				}
			});
		}
		
	})
	
}

function removeMyTree(id){
	
	 swal({
			title : '',
			text : "해당 폴더를 삭제하시겠습니까?",
			type : 'info',
			showCancelButton : true,
			confirmButtonClass : "btn-danger",
			confirmButtonText : "예",
			cancelButtonText : "아니오",
			closeOnConfirm : false,
			closeOnCancel : true
		}, function(isConfirm) {
			if (isConfirm) {
				/**
				 * 삭제 시 해당분류에 문서가 포함되어 있으면 삭제 할 수 없습니다.
				 */
				var param = "mstrTreeFormDTO.targetCategory=T&mstrTreeFormDTO.targetId=" + id;
				var url = _contextPath + "/edms/tr/searchRemoveMyTree.action?";
				new Ajax.Request(url, {
					method : 'POST',
					parameters : param,
					asynchronous : false,
					onSuccess : function(response) 
					{
						
						if(response.responseText == "SUCCESS")
						{
							mydoctree.deleteItem(id);
							alert("삭제되었습니다.");
						}else{
							alert("삭제에 실패하였습니다.");
						}
					}
				});
			}else{
				return false;
			}

		});
	
}

function rcAtfileChange(val){
	var filename = val.files[0].name;
	
	
	
	j$(val).siblings('.upload-name').val(filename);

}

function rcMkfileChange(val){
	
	var filename = val.files[0].name;
	
	var _fileLen = filename.length;
	 
    var _lastDot = filename.lastIndexOf('.')+1;
 
    // 확장자 명만 추출한 후 소문자로 변경
    var _fileExt = filename.substring(_lastDot, _fileLen).toLowerCase();

	if(_fileExt != 'mku'){
		alert('mku 확장자만 선택할 수 있습니다.');
		rcAtfileDelete(val);
	}else{
		j$(val).siblings('.upload-name').val(filename);
	}
	
	
}

function rcAtfileDelete(val){
	val.value="";
	j$(val).siblings('.upload-name').val("파일선택");
}

function markupfileDelete(val){
	val.value="";
	j$(val).siblings('.upload-name').val("마크업선택");
}

function doChangeTreeClose(){
	PopWin.hide('ChangeTreeDiv');
	PopWin.setStatus('ChangeTreeDiv', '');
}

function markupfileSelect(markup_row_id,val){
	var doc_seq = val;
	$('markup_target_doc').value=markup_row_id.id;
	
	var url = _contextPath + "/edms/dc/markupFileList.action?doc_seq="+ doc_seq;
	markupgrid.clearAndLoad(url + "&searchFormDTO.gridNames="
			+ $G('markupgridbox').getIds(), function(){
		PopWin.show('markupListDiv');
	});
	
	
}

function markupPreview(doc_seq, markupseq){
	var param = "?socketDTO.f_category_id=" + escape(doc_seq)+"&socketDTO.sftp="+_ses.sftp;
	var url = _contextPath + "/edms/socket/seDocumentOpen.action";
	new Ajax.Request(url, {
		method : 'POST',
		parameters : param.toQueryParams(),
		asynchronous : false,
		onSuccess : function(response) {
			DocumentOpen(response.responseText,"M",markupseq);
		}
	});
}

function markupgridboxonRowDblClicked(rId,cInd){
	
	var markupseq = $G('markupgridbox').cells(rId,'markupseq').getValue();
	
	var target_id = $('markup_target_doc').value;
	
	$(target_id).value=markupseq;
	
	PopWin.hide('markupListDiv');
}


function excelSetting(kind, status) {
    if (kind == "F") {
        var def = j$('#default_form').is(':checked');
        var personal = j$('#personal_form').is(':checked');

        if (def == true && personal == true && status == "D") {
            j$('#personal_form').prop('checked', !personal);
        } else if (def == true && personal == true && status == "P") {
            j$('#default_form').prop('checked', !def);
        }
        /*else if(def == false && personal == false)
        		{
        			j$('#default_form').prop('checked',!def);
        		}*/
        else {
            j$('#default_form').prop('checked', def);
            j$('#personal_form').prop('checked', personal);
        }

    } else if (kind == "D") {
        var standard = j$('#standard').is(':checked');
        var all = j$('#all').is(':checked');

        if (standard == true && all == true && status == "S") {
            j$('#all').prop('checked', !standard);
        } else if (standard == true && all == true && status == "A") {
            j$('#standard').prop('checked', !all);
        }
        /*else if(standard == false && all == standard)
        		{
        			j$('#standard').prop('checked',!all);
        		}*/
        else {
            j$('#standard').prop('checked', standard);
            j$('#all').prop('checked', all);
        }
    }
}
function excelDownload()
{
	var def = j$('#default_form').is(':checked');
    var personal = j$('#personal_form').is(':checked');
	var standard = j$('#standard').is(':checked');
    var all = j$('#all').is(':checked');

	if(def == false && personal == false)
	{
		alert("폼을 선택하여 주시기 바랍니다.");
		return;	
	}else if(standard == false && all == false)
	{
		alert("다운로드 방식을 선택하여 주시기 바랍니다.");
		return;
	}
	
	if(def == true && standard == true)
	{
		//기준다운로드
		doDownloadIndex("def");
	}else if(def == true && all == true)
	{
		//전체기준다운
		doDownloadAllIndex("def");
	}else{
		if(standard == true)
		{
			//개인별 양식 기준다운로드
			doDownloadIndex("personal");
		}else if(all == true)
		{
			//개인별 양식 전체기준다운로드
			doDownloadAllIndex("personal");
		}
	}
}

function dataviewPreview(src){
	if (!myPop) {
        myPop = new dhtmlXPopup();
    }
			
	var view_img = new Image();
	view_img.src = _contextPath+src
	view_img.width = (myDataView._obj.clientWidth/2) ;
	view_img.height = myDataView._obj.clientHeight;
	view_img.className=grayScaleClassNm;
		    	
	view_img.onerror = "";
		    	
	view_img.onload = function(){
		myPop.attachObject(view_img);
    	
        var x = (myDataView._obj.clientWidth/3); // returns left position related to window
        var y = myDataView._obj.clientHeight + 50; // returns top position related to window
        var w = (myDataView._obj.clientWidth/2);
        var h = myDataView._obj.clientHeight;
		
    	myPop.show(x,y,w,h);	
//       	img_tooltip = setTimeout(function(){
//        }, 1000);
	};
	
}

function dataviewPreviewOut(){
//	clearTimeout(img_tooltip);
	if(myPop)
	myPop.hide();
}

//20210819 자료등록 프로그래스바 기능 추가
function ProgressBarPlaying(flag, num, total){
	var ele = document.getElementById('progressing');
	if(flag == "START"){
		ele.style.width="0%";
        ele.innerHTML="0%";
		PopWin.show("progressDiv");
		PopWin.setStatus('progressDiv', "전체건수 : " + total+ "건 / 진행건수 : 0건");
	}
	else if(flag == "PLAY"){
		var width = Math.floor((num/total)*100);
		ele.style.width=width+"%";
        ele.innerHTML=width+"%";
        PopWin.setStatus('progressDiv', "전체건수 : " + total+ "건 / 진행건수 : "+ num + "건");
	}
	else if(flag == "FINAL"){
		ele.style.width="100%";
        ele.innerHTML="100%";
        PopWin.setStatus('progressDiv', "전체건수 : " + total+ "건 / 진행건수 : "+ total + "건");
        PopWin.hide("progressDiv");
	}
	else{
		PopWin.hide("progressDiv");
	}	
}

function setCommtsList(){
	var url = _contextPath + "/edms/dc/seCommtsList.action?";
	new Ajax.Request(url, {
		method : 'POST',
		asynchronous : false,
		onSuccess : function(response) {
			var json = eval('(' + response.responseText + ')');
			var str="";
			for(var i = 0;i < json.length;i++){
				if(i == 0) {
					str = "<label><input type='radio' name='commtsList' value='" + json[i].cvalue + "' onclick='commtsCheck()' checked='checked'>" + json[i].cvalue + "</label><br>"; 
				}else{
					str += "<label><input type='radio' name='commtsList' value='" + json[i].cvalue + "' onclick='commtsCheck()'>" + json[i].cvalue + "</label><br>";
				}
			}
			str += "<textarea id='etcCommts' cols='40' rows='4' readonly='true'></textarea>";
			//alert(str);
			$("commtsTd").innerHTML = str;
		}
	});
}

function commtsCheck(){
	var commtsList = j$(':radio[name="commtsList"]:checked').val();
	if(commtsList === "기타(다른사유 작성)"){
		j$("#etcCommts").removeAttr("readonly");
	}else{
		j$("#etcCommts").val("");
		j$("#etcCommts").attr("readonly",true);
	}
}

