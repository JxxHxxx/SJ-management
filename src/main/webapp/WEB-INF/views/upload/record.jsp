<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
    <div class="content">
        <h2>판매 내역 자료 업로드</h2>
        <div class="btn-gr">
            <div></div>
            <button id="upload-btn">등록</button>
        </div>
        <div id="upload-box" onclick="document.getElementById('fileInput').click();">
            <span id="file-name">파일 첨부</span>
        </div>
        <input type="file" id="fileInput">
        <span class="noUploadFileMsg" style="display: none;">첨부된 파일이 없습니다. 파일을 첨부해주세요</span>
        <span class="unSupportedFormatMsg" style="display: none;">지원하지 않은 파일 형식입니다. xlsx 형식으로 첨부하세요</span>
        <span class="unValidatedFileMsg" style="display: none;">유효하지 않은 파일입니다. 판매 내역 파일 내부를 수정하지 마세요</span>
    </div>
<script>
    const uploadBox = document.getElementById('upload-box');
    const uploadBtn = document.getElementById('upload-btn');
    const fileInput = document.getElementById('fileInput');
    const fileNameDisplay = document.getElementById('file-name');
    const noUploadFileMsg = document.querySelector('.noUploadFileMsg');
    const unSupportedFormatMsg = document.querySelector('.unSupportedFormatMsg');
    const unValidatedFileMsg = document.querySelector('.unValidatedFileMsg');

    fileInput.addEventListener('change', function (event) {
        let files = event.target.files;
        if (files.length > 0) {
            noUploadFileMsg.style.display = 'none';
            unSupportedFormatMsg.style.display = 'none';
            unValidatedFileMsg.style.display = 'none';
        }
        displayFileNames(files);
    });

    uploadBox.addEventListener('dragover', function (event) {
        event.preventDefault(); // 기본 동작(다운로드 방지)
        uploadBox.style.borderColor = 'red'; // 드래그 중 스타일 변경
    });

    uploadBox.addEventListener('dragleave', function () {
        uploadBox.style.borderColor = '#007bff'; // 드래그 해제 시 원래 색상으로
    });

    uploadBox.addEventListener('drop', function (event) {
        noUploadFileMsg.style.display = 'none';
        unSupportedFormatMsg.style.display = 'none';
        unValidatedFileMsg.style.display = 'none';
        event.preventDefault(); // 기본 동작 방지 (파일 다운로드 방지)

        uploadBox.style.borderColor = '#007bff';

        let files = event.dataTransfer.files;
        fileInput.files = files; // input 요소에 파일 등록
        displayFileNames(files);
    });

    uploadBtn.addEventListener('click', function () {
        noUploadFileMsg.style.display = 'none';
        unSupportedFormatMsg.style.display = 'none';
        unValidatedFileMsg.style.display = 'none';
        let files = fileInput.files;
        // 파일을 업로드하지 않았을 때
        if (files.length === 0) {
            noUploadFileMsg.style.display = 'inline';
            return;
        }

        // 파일 형식이 xlsx 가 아닐 때
        if (!files[0].name.endsWith(".xlsx")) {
            unSupportedFormatMsg.style.display = 'inline';
            return;
        }

        let formData = new FormData();
        for (let file of files) {
            formData.append('file', file);
        }
        // 서버 요청
        fetch('/manage/api/sale-records', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json()) // JSON 파싱
            .then(data => {
                if (data.responseCode === "S_0001") {
                    if (data.body === 0) {
                        alert('✅ 업로드한 모든 판매 내역이 이미 반영되어 있습니다.');
                    }
                    else {
                        alert('✅ 판매 내역' + data.body +  '건이 성공적으로 업로드 되었습니다');
                    }
                }
                if (data.responseCode === "F_SR01") {
                    unValidatedFileMsg.style.display = 'inline';
                    console.error(data.body)
                    return;
                }

                if (data.responseCode === "F_SR03") {
                    unSupportedFormatMsg.style.display = 'inline';
                    console.error(data.body)
                    return;
                }
            })
            .catch(error => console.error('업로드 오류:', error));

    });

    function displayFileNames(files) {
        let fileNames = Array.from(files).map(file => file.name).join(', ');
        fileNameDisplay.textContent = fileNames || '파일 첨부';
    }

</script>
