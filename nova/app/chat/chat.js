import { OpenAI } from 'openai';

const openai = new OpenAI({ apiKey: '', dangerouslyAllowBrowser: true });

// export async function generatePrompts(prompt) {
//   const response = await openai.completions.create({
    
//     model: 'ft:davinci-002:personal::9Ks9WhVf',
//     prompt: prompt,
//     max_tokens: 255,
//     temperature: 1,
//     stop: ['\n'],

//   });
  
//   return response.choices[0].text.trim();
// }

export async function generatePrompts(prompt) {
  const response = await openai.chat.completions.create({
    model: 'ft:gpt-3.5-turbo-0125:personal::9KxnJ9yI',
    messages: [{ role: 'system', content: "You are a helpful assistant that solves questions about neoris. Any other topics should be responded with: Im sorry, I cant help with that." }, { role: 'user', content: prompt}],
    max_tokens: 255,
    temperature: 1,
    stop: ['\n'],
  });
  
  return response.choices[0].message.content.trim();
}
