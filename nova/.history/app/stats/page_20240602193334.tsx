// /app/stats/page.tsx
"use client";

import React, { useState, useEffect } from 'react';
import VisitPieChart from '@/app/components/VisitPieChart';
import withAuth from '../components/HOC/withAuth';
import NavbarComponent from '../components/NavBar';


const StatsPage = () => {
    const [visitData, setVisitData] = useState({
        totalVisits: 0,
        visitsWithAccount: 0,
        visitsWithoutAccount: 0,
    });

    useEffect(() => {
        // Fetch data from your API endpoint
        fetch('http://localhost:8080/eventLog/visit-metrics')
          .then((response) => response.json())
          .then((data) => {
            // Update state with the fetched data
            setVisitData({
              totalVisits: data.totalVisits,
              visitsWithAccount: data.visitsByRegisteredUsers,
              visitsWithoutAccount: data.totalVisits - data.visitsByRegisteredUsers,
            });
          })
          .catch((error) => console.error('Error fetching data:', error));
      }, []); // Empty dependency array ensures the effect runs only once

    return (
        <div>
            <NavbarComponent/>
            <h1>Dashboard</h1>
            <VisitPieChart
            totalVisits={visitData.totalVisits}
            visitsWithAccount={visitData.visitsWithAccount}
            visitsWithoutAccount={visitData.visitsWithoutAccount}
            />
        </div>
    );
};

export default withAuth(StatsPage);