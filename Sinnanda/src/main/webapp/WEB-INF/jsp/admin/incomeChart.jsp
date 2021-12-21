<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.6.0/chart.min.js"></script>

</head>
<body>
	<h1>정산</h1>
<canvas id="myChart" width="400" height="400"></canvas>
	
	<script>
		$.ajax({
			type:'get',
			url:'/getTotalInOfMonthByYear?year=2020',
			success:function(json){
				console.log(json);
				
				let myData = [];
				myData.push(json.january);
				myData.push(json.february);
				myData.push(json.march);
				myData.push(json.april);
				myData.push(json.may);
				myData.push(json.june);
				myData.push(json.july);
				myData.push(json.august);
				myData.push(json.september);
				myData.push(json.october);
				myData.push(json.november);
				myData.push(json.december);
				
				// chart.js
				const ctx = document.getElementById('myChart').getContext('2d');
				const myChart = new Chart(ctx, {
				    type: 'bar',
				    data: {
				        labels: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				        datasets: [{
				            label: '# of Votes',
				            data: myData,
				            backgroundColor: [
				                'rgba(255, 99, 132, 0.2)',
				                'rgba(54, 162, 235, 0.2)',
				                'rgba(255, 206, 86, 0.2)',
				                'rgba(75, 192, 192, 0.2)',
				                'rgba(153, 102, 255, 0.2)',
				                'rgba(255, 159, 64, 0.2)',
				                'rgba(255, 99, 132, 0.2)',
				                'rgba(54, 162, 235, 0.2)',
				                'rgba(255, 206, 86, 0.2)',
				                'rgba(75, 192, 192, 0.2)',
				                'rgba(153, 102, 255, 0.2)',
				                'rgba(255, 159, 64, 0.2)'
				            ],
				            borderColor: [
				                'rgba(255, 99, 132, 1)',
				                'rgba(54, 162, 235, 1)',
				                'rgba(255, 206, 86, 1)',
				                'rgba(75, 192, 192, 1)',
				                'rgba(153, 102, 255, 1)',
				                'rgba(255, 159, 64, 1)',
				                'rgba(255, 99, 132, 0.2)',
				                'rgba(54, 162, 235, 0.2)',
				                'rgba(255, 206, 86, 0.2)',
				                'rgba(75, 192, 192, 0.2)',
				                'rgba(153, 102, 255, 0.2)',
				                'rgba(255, 159, 64, 0.2)'
				            ],
				            borderWidth: 1
				        }]
				    },
				    options: {
				        scales: {
				            y: {
				                beginAtZero: true
				            }
				        }
				    }
				});
				
			}
		});
	</script>

</body>
</html>