import { OpenAI } from 'openai';

const openai = new OpenAI({ apiKey: '', dangerouslyAllowBrowser: true });

export async function generatePrompts(prompt) {
  const response = await openai.completions.create({
    
    // model: 'ft:davinci-002:personal::9Ks9WhVf',
    model: 'ft:gpt-3.5-turbo-1106:personal::9KtHviU9',
    prompt:prompt,
    max_tokens: 255,
    temperature: 0.4

  });
  
  return response.choices[0].text.trim();
}