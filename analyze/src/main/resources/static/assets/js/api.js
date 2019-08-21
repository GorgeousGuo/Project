/**
 *
 *api.js
 * 1·主要用于前端页面与后端的交互
 * 前端：HTML+CSS+JavaScript开发的浏览器识别的web程序
 * 后端：Java开发服务程序，通过HTTP协议提供Web API接口
 * 前端和后端交互：通信协议用的是HTTP协议，
 */


function creationRanking(id) {
    //HTTP Method:GET
    //HTTP URL:请求地址（服务器提供的API接口）
    $get({
        url:"analyze/dynasty_count",
        dataType:"json",
        method:"get",
        success:function (data,status,xhr) {

            //echarts图表对象
            var myChart = echarts.init(document.getElementById(id));
            //x轴，y轴信息
            var options = {
                //图表标题
                title: {
                    text: '创作排行榜'
                },
                tooltip: {},
                //柱状图提示信息
                legend: {
                    data: ['数量（首）']
                },
                //X轴
                xAxis: {
                    data: [dynasty_Count]
                },
                //Y轴
                yAxis:  [
                    {
                        type : 'count'
                    }
                ],
                series: [{
                    name: '创作数量',
                    type: 'bar',
                    data: []
                }]
            };

            //List<dynastyCount>
            for (var i = 0; i < data.length; i++) {
                var dynasty_Count  = data[i];
                options.xAxis.data.push(dynasty_Count.dynasty);
                options.series[0].data.push(dynasty_Count .count);
            }
            myChart.setOption(options, true);
        },
        error:function (xhr,status,errot) {
        }
    });
}
function cloudWord(id) {
    $.get({
        url:"/analyze/word_cloud",
        dataType:"json",
        method:"get",
        success:function (data,status,xhr) {
            var myChar = echarts.init(document.getElementById(id)
            );
            var options = {
                series:[{
                    type:'wordCloud',
                    shape:'pentagon',
                    left:'center',
                    top:'center',
                    width:'80%',
                    right:null,
                    bottom:null,
                    sizeRange:[12,60],
                    rotationRange:[-90,90],
                    rotationStep:45,
                    gridSize:8,
                    drawOutOfBound:false,
                    textStyle:{
                        normal:{
                            fontFamily:'sans-serif',
                            fontWeigth:'bold',
                            color:function () {
                                return'rgb('+[
                                    Math.round(Math.random()*160),
                                    Math.round(Math.random()*160),
                                    Math.round(Math.random()*160)
                                ].join(',')+')';
                            }
                        },
                        emphasis:{
                            shadowBlur:10,
                            shadowColor:'#333'
                        }
                    },
                    data:[]
                }]
            };
            for(var i=0;i<data.length;i++){
                var wordCount = data[i];
                options.series[0].data.push({
                    name:wordCount.word,
                    value:wordCount.count,
                    textStyle:{
                        normal:{},
                        emphasis:{}
                    }
                });
            }
            myChar.setOption(options,true);
        },
        error:function (xhr,status,error) {
        }
    });

}