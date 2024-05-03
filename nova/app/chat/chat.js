import { OpenAI } from 'openai';

const openai = new OpenAI({ apiKey: '', dangerouslyAllowBrowser: true });

export async function generatePrompts(prompt) {
  const response = await openai.completions.create({
    
    model: 'ft:davinci-002:personal::9Ks9WhVf',
    
    prompt:prompt,
    max_tokens: 255,
    temperature: 0.4

  });
  
  return response.choices[0].text.trim();
}