#!/usr/bin/jjs -fx -scripting
var PieChart = javafx.scene.chart.PieChart;
var data = JSON.parse($EXEC('cat ' + __DIR__ + 'chart.json'));
var pieChartData = javafx.collections.FXCollections.observableArrayList();
for(var key in data){
    pieChartData.add(new PieChart.Data(key, data[key]));
}
var chart = new PieChart(pieChartData);
chart.setTitle("Population of the Continents");
var group = new javafx.scene.Group(chart);
var scene = new javafx.scene.Scene(group);
$STAGE.setWidth(500);
$STAGE.setHeight(500);
$STAGE.setScene(scene);
$STAGE.show();