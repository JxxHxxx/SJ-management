<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Jxx Util</title>
    <style>
        .top-container {
            width: 500px;
            margin : 0 auto;
        }
        .btn-gr {
            display: flex;
            justify-content: space-between;
        }

        #upload-box {
            border: 2px dashed #007bff;
            padding: 10px;
            cursor: pointer;
        }

        #upload-box:hover {
            color: gray
        }

        #fileInput {
            display: none;
        }

        #upload-btn {
            margin-top: 10px;
            margin-bottom: 5px;
            padding: 5px 20px;
            background: #007bff;
            color: white;
            border: none;
            cursor: pointer;
        }
        .warn-msg {
            padding: 10px;
            color: red;
            font-size: 13px;
        }
    </style>
</head>
<body>

<div class="top-container">
    <div class="btn-gr">
        <div></div>
        <button id="upload-btn">등록</button>
    </div>
    <div id="upload-box" onclick="document.getElementById('fileInput').click();">
        <span id="file-name">파일 첨부</span>
    </div>
    <input type="file" id="fileInput">
    <span class="warn-msg" style="display: none;">첨부된 파일이 없습니다. 파일을 첨부해주세요</span>
</div>

<script>
    const uploadBox = document.getElementById('upload-box');
    const uploadBtn = document.getElementById('upload-btn');
    const fileInput = document.getElementById('fileInput');
    const fileNameDisplay = document.getElementById('file-name');
    const warnMsg = document.querySelector('.warn-msg');

    fileInput.addEventListener('change', function (event) {
        let files = event.target.files;
        if (files.length > 0) {
            warnMsg.style.display = 'none';
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
        event.preventDefault(); // 기본 동작 방지 (파일 다운로드 방지)
        uploadBox.style.borderColor = '#007bff';

        let files = event.dataTransfer.files;
        fileInput.files = files; // input 요소에 파일 등록
        displayFileNames(files);
    });

    uploadBtn.addEventListener('click', function () {
        let files = fileInput.files;

        if (files.length === 0) {
            warnMsg.style.display = 'inline'; // 경고 메시지 보이기
            return;
        }

        let formData = new FormData();
        for (let file of files) {
            formData.append('file', file);
        }

        fetch('/api/record', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json()) // JSON 파싱
            .then(data => {
                if (data.responseCode === "S_0001") {
                    alert('✅ upload success: ' + data.body);
                }
                else {
                    alert("❌ upload fail" +
                        "\nerror code : ' + data.responseCode" +
                        "\nmessage : " + data.body);
                }
            })
            .catch(error => console.error('업로드 오류:', error));

    });

    function displayFileNames(files) {
        let fileNames = Array.from(files).map(file => file.name).join(', ');
        fileNameDisplay.textContent = fileNames || '파일 첨부';
    }

</script>

</body>
</html>
