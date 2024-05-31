import { NextApiRequest, NextApiResponse} from 'next';

export async function GET(req: NextApiRequest, res: NextApiResponse) {
    try {
        const stats = await getStats();
        return res.status(200).json(stats);
    } catch (error) {
        return res.status(500).json({error: 'Failed to fetch statistics'})
    }
}

// Mock function, replace with your actual database fetching function
async function getStats() {
    return {
        visitsPerDay: [{ date: '2024-01-01', count: 100 }, { date: '2024-01-02', count: 150 }],
        clicksOnComponent: { componentA: 120, componentB: 80 },
    };
}