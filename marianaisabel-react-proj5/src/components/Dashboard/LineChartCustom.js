import React from "react";
//import { LineChart, LineSeries, PointSeries,LinearYAxis,LinearAxisLine,LinearYAxisTickLabel,LinearYAxisTickSeries,LinearXAxis,LinearXAxisTickLine, LinearXAxisTickSeries,LinearXAxisTickLabel,LinearYAxisTickLine} from 'reaviz';
import {
    LineChart, Line, XAxis, YAxis, CartesianGrid, Legend, Tooltip, Label
} from 'recharts';
import { connect } from 'react-redux'


function formatDateXAxis(tickItem){
    console.log(tickItem)
    if(tickItem instanceof Date){
    return tickItem.toISOString().slice(0,10);
    }
}

function LineChartCustom ({usersPerDay=[],...props}){
        let dataSet=usersPerDay;
        console.log(dataSet);
        let dataChartFormat=[];
        let obj;
        let maximum=0;

        for(var i = 0; i < dataSet.length ; i++){
            console.log(dataSet[i]);
            console.log(dataSet[i].key);
            obj =  { date: new Date ((dataSet[i].key)),
                     users: dataSet[i].data
                }
            console.log(obj);
            if(dataSet[i].data>maximum){
                maximum=dataSet[i].data;
            }
            dataChartFormat.push(obj)
        }

        for(var i = 0; i < dataChartFormat.length ; i++){
            console.log(dataChartFormat[i])
        }
        
        dataChartFormat.sort(function (a, b) {
            let keyA = a.date;
            let keyB = b.date;
    
            if (keyA < keyB) return -1;
            if (keyA > keyB) return 1;
            return 0;
            
        });
     //   tickFormatter={formatDateXAxis}   
    return (
        <div className="charts">
            <h4>Número de registos de utilizadores por dia </h4>
            <LineChart width={400} height={200} data={dataChartFormat} title="Número de registos de utilizadores por dia" 
            margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
                <XAxis dataKey="date"    tickFormatter={formatDateXAxis} tick={{fontSize: 14,  fill: 'rgb(81, 80, 150)'}}  >    </XAxis>
                <YAxis tick={{fontSize: 14, fill: 'rgb(81, 80, 150)'}} >
                <Label
                style={{
                    textAnchor: "middle",
                    fontSize: "100%",
                    fill: "rgb(81, 80, 150)",
                }}
                angle={270} 
                value={"Número de registos"} 
                />
                </YAxis >
                <Tooltip />
                <CartesianGrid strokeDasharray="3 3" />
                <Line type="monotone" dataKey="users" stroke="darkblue"  fill='rgb(81, 80, 150)' activeDot={{ r: 7 }}/>
            </LineChart>
        </div>
    );
   
}
export default  LineChartCustom;