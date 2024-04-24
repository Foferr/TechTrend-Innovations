// pages/index.js

export async function getServerSideProps() {
  const res = await fetch('http://localhost:3000/api/data'); // Update URL
  const data = await res.json();

  return {
    props: {
      data,
    },
  };
}

export default function Home({ data }: { data: any }) {
  return (
    <div>
      <h1>
        {data.map((user: any) => (
          <div key={user.id}>
            <h2>{user.firstName}</h2>
            <p>{user.lastName}</p>
          </div>
        ))}
      </h1>
    </div>
  );
}
