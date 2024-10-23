<%@ include file="header.jsp" %>

   <h1>

           <c:choose>

               <c:when test="${province == null}">

                   <h1>Crear province</h1>

               </c:when>

               <c:otherwise>

                   <h1>Update province</h1>

               </c:otherwise>

           </c:choose>

       </h1>



<form action="provinces" method="post">

       <input type="hidden" name="id" value="${provicne != null ? province.id : ''}" />

       <input type="hidden" name="action" value="${province == null ? 'insert' : 'update'}" />



           <label for="code">Codigo:</label>

           <input type="text" name="code" id="code" value="${province != null ? province.code : ''}" required />



           <label for="name">Name:</label>

           <input type="text" name="name" id="name" value="${province != null ? province.name : ''}" required />



           <select name"id_region">

               <option value=""></option>

               <c:forEach var="region" items="${listRegions}">

                   <option value="${region.id}">${region.name}</option>

               </c:forEach>

           </select>



           <c:choose>

                       <c:when test="${province == null}">

                           <input type="submit" value="Crear Provincia" />

                       </c:when>

                       <c:otherwise>

                           <input type="submit" value="Actualizar Provincia" />

                       </c:otherwise>

                   </c:choose>





</form>