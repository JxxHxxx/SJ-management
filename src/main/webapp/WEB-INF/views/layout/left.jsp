<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<div class="sidebar">
    <h2>쌀먹으로 아파트 살끄니까</h2>
    <ul>
        <li id="saleStatListItem">판매 통계</li>
        <li id="enrollSaleRecordListItem">판매 내역 업로드</li>
        <li id="gameAccountManageListItem">게임 계정 관리</li>
    </ul>
</div>

<script>
    const saleStatListItem = document.getElementById("saleStatListItem");
    saleStatListItem.addEventListener('click', function () {
        window.location.href = '/manage/saleRecord/statMain.do'
    })

    const enrollSaleRecordListItem = document.getElementById('enrollSaleRecordListItem');
    enrollSaleRecordListItem.addEventListener('click', function () {
        window.location.href = '/manage/saleRecord/upload.do'
    })
</script>

