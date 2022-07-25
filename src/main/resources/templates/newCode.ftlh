<html>
<head>
    <title>Create</title>
    <script>
        function send() {
            let object = {
                "code": document.getElementById("code_snippet").value
                ""
            };

            let json = JSON.stringify(object);

            let xhr = new XMLHttpRequest();
            xhr.open("POST", '/api/code/new', false)
            xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            xhr.send(json);

            if (xhr.status == 200) {
                alert("Success!");
            }
        }
    </script>

</head>
<body>
<textarea id="code_snippet" rows="10" cols="50" placeholder="//write your code here"></textarea>
<br>
<br>
<label for="time_restriction">Time restriction: </label><input id="time_restriction" type="text"/>
<br>
<br>
<label for="views_restriction">Views restriction: </label><input id="views_restriction" type="text"/>
<br>
<br>
<button id="send_snippet" type="submit" onclick="send()">Submit</button>
</body>
</html>