<#include "/pages/layout/layout.ftl"/> 
<@layout>

<style>
ul {
	padding-left: 10px;
}
table th,td {
	text-align: center;
}
</style>

<div class="page-content-inner">
	<div class="row">

		<div class="col-md-9">

			<div class="portlet light">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-bar-chart font-blue-sharp"></i> <span
							class="caption-subject bold uppercase font-blue-sharp">新增违法建设</span>
					</div>
					<div class="actions" style="margin-top:6px;">
						<a href="javascript:void(0);">查看更多</a>
					</div>
				</div>
				<div class="portlet-body " style="min-height:150px;">
					<table id="new-table" class="table table-bordered  table-hover">
						<thead>
							<tr>
								<th>受理编号</th>
								<th>当事人名称或项目</th>
								<th>违章所在</th>
								<th>发现时间</th>
								<th>登记时间</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
			</div>

			<div class="portlet light">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-bar-chart font-blue-sharp"></i> <span
							class="caption-subject bold uppercase font-blue-sharp">存量违法建设</span>
					</div>
					<div class="actions" style="margin-top:6px;">
						<a href="/admin/violation_build/stock">查看更多</a>
					</div>
				</div>
				<div class="portlet-body " style="min-height:150px;">
					<table id="violationBuild-table" class="table table-bordered  table-hover">
						<thead>
							<tr>
								<th>受理编号</th>
								<th>当事人名称或项目</th>
								<th>违章所在</th>
								<th>发现时间</th>
								<th>登记时间</th>
							</tr>
						</thead>
						<tbody>
						
						</tbody>
					</table>
				</div>
			</div>

			<div class="portlet light">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-bar-chart font-blue-sharp"></i> <span
							class="caption-subject bold uppercase font-blue-sharp">举报投诉建设</span>
					</div>
					<div class="actions" style="margin-top:6px;">
						<a href="/admin/report">查看更多</a>
					</div>
				</div>
				<div class="portlet-body " style="min-height:150px;">
					<table id="report-table" class="table table-bordered  table-hover">
						<thead>
							<tr>
								<th>受理编号</th>
								<th>主题</th>
								<th>详细地址</th>
								<th>违章所在</th>
								<th>举报时间</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
			</div>

		</div>
		<!--  middle -->

		<div class="col-md-3">
			<div class="row">
				<div class="portlet light">
					<div class="portlet-title">
						<div class="caption">
							<i class="icon-bubble font-green-haze"></i> <span
								class="caption-subject bold uppercase font-green-haze">待办事项</span>
						</div>
					</div>
					<div class="portlet-body index-title">
						<div class="alert alert-info">您有（0）条消息回复</div>
						<div class="alert alert-info">您有（90）条待办事宜</div>
						<div class="alert alert-info">您有（55）条咨询投诉临近逾期</div>
						<div class="alert alert-info">您有（99）条咨询投诉逾期</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="portlet light">
					<div class="portlet-title">
						<div class="caption">
							<i class="glyphicon glyphicon-question-sign font-green-haze"></i>
							<span class="caption-subject bold uppercase font-green-haze">通知公告</span>
						</div>
						<div class="actions" style="margin-top:6px;">
							<a href="/admin/sys_notice_sys">查看更多</a>
						</div>
					</div>
					<div id="notice" class="portlet-body index-title">
						<ul>
							
						</ul>
					</div>
				</div>
			</div>
		</div>
		<!-- left -->

	</div>
</div>

</@layout>
