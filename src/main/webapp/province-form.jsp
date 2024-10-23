<%@ include file="header.jsp" %>

    <h1> Crear nueva provincia </h1>

    <!-- Cuando una cadena de caracteres incluye a otra esta debe llevar comillas simples -->
                    <c:choose>
                        <c:when test="${province == null}">
                        <h1>Crear Provincia</h1>
                        </c:when>
                        <h1>Actualizar Provincia</h1>
                        <c:otherwise>
                </c:otherwise>
             </c:choose>

    <form action="" method="post">
        <input type="hidden" name="id" value="${province != null ? province.id : ''}" />
        <input type="hidden" name="action" value="${province == null ? 'insert' : 'update'}" />

        <label for="code">Código</label>
        <input type="text" name="code" id="code" value="${province != null ? region.code : ''}" required />

         <label for="name">nombre</label>
         <input type="text" name="name" id="name" value="${province != null ? province.name : ''}" required />

         <select name="id_region">
            <option value=""></option>
            <c:forEach var="region" items="${listRegions}">
                <option value="${region.id}">${region.name}</option>
            </c:forEach>
         </select>

         <!-- Cuando una cadena de caracteres incluye a otra esta debe llevar comillas simples -->
                <c:choose>
                    <c:when test="${province == null}">
                    </c:when>
                    <c:otherwise>
            </c:otherwise>
         </c:choose>
    </form>

<%@ include file="footer.jsp" %>