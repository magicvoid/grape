<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="header">
	<div id="title">
		<h4>grape API Test -- by magic</h4>
		<div class="progress progress-info progress-striped">
			<div class="bar" style="width: 70%">total progress</div>
		</div>
		<hr />
		<p class="text-info">
			<i class="icon-user"></i> account | 
			<button id="getCredential" class="btn">getCredential</button>
			<button id="getQRCode" class="btn">getQRCode</button>
			<button id="updateQRCode" class="btn">updateQRCode</button>
			<button id="userRegist" class="btn">userRegist</button>
			<button id="userLogin" class="btn">userLogin</button>
			<button id="validateCredential" class="btn">validateCredential</button>
		</p>
		<hr />
		<p class="text-info">
			<i class="icon-star"></i> shop |  
			<button id="getCredential" class="btn">getCredential</button>
			<button id="getQRCode" class="btn">getQRCode</button>
			<button id="updateQRCode" class="btn">updateQRCode</button>
			<button id="userRegist" class="btn">userRegist</button>
			<button id="userLogin" class="btn">userLogin</button>
			<button id="validateCredential" class="btn">validateCredential</button>
		</p>
		<hr />
		<p class="text-info">
			<i class="icon-star"></i> admin |  
			<button id="createShop" class="btn">createShop</button>
			<button id="getQRCode" class="btn">getQRCode</button>
			<button id="updateQRCode" class="btn">updateQRCode</button>
			<button id="userRegist" class="btn">userRegist</button>
			<button id="userLogin" class="btn">userLogin</button>
			<button id="validateCredential" class="btn">validateCredential</button>
		</p>
		<hr />
		<shiro:user>
			<div class="btn-group pull-right">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#"> <i class="icon-user"></i> <shiro:principal property="name" /> <span class="caret"></span>
				</a>

				<ul class="dropdown-menu">
					<shiro:hasRole name="admin">
						<li><a href="${ctx}/admin/user">Admin Users</a></li>
						<li class="divider"></li>
					</shiro:hasRole>
					<li><a href="${ctx}/profile">Edit Profile</a></li>
					<li><a href="${ctx}/logout">Logout</a></li>
				</ul>
			</div>
		</shiro:user>
	</div>
</div>