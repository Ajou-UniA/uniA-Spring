API sheet : https://www.notion.so/love-leah/API-Sheet-7070d02c6fa94a5da8a7323ef1cad2f9 <br/>
포트번호 : 8081 <br/>

mySQL 연결방법: (AWS RDS 사용 전까지) application.yml 파일에서   <br/>

url: jdbc:mysql://localhost:3306/jisinDB?serverTimezone=Asia/Seoul&characterEncoding=UTF-8 <br/>
mysql에 생성한 사용자 계정 정보를 써야 합니다. <br/>
username: root <br/>
password: 1234 <br/>

jisinDB, root, 1234 부분을 자신의 로컬 데이터베이스에 맞게 수정하면 됩니다
