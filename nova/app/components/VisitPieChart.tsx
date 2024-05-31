import React from 'react';
import {ResponsivePie} from '@nivo/pie';

interface VisitPieChartProps {
    totalVisits: number;
    visitsWithAccount: number;
    visitsWithoutAccount: number;
}

const VisitPieChart: React.FC<VisitPieChartProps> = ({ totalVisits, visitsWithAccount, visitsWithoutAccount}) => {
    const data = [
        {id: 'With Account', label: 'With Account', value: visitsWithAccount},
        {id: 'Without Account', label: 'Without Account', value: visitsWithoutAccount},
    ];

    return (
        <div style={{height: '300px', textAlign: 'center'}}>
            <h2>Key Metric: Visits</h2>
            <ResponsivePie
                data={data}
                colors={['#2ecc71', '#3498db']}
                innerRadius={0.5}
                padAngle={0.7}
                cornerRadius={3}
                borderWidth={1}
                borderColor={{from : 'color', modifiers: [['darker', 0.2]]}}
                animate={true}
            />
            <p>Total Visits: {totalVisits}</p>
        </div>
    )
}

export default VisitPieChart;