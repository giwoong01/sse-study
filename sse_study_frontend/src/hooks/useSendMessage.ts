import { SendMessageApi } from "../api/SendMessageApi.ts";

export const useSendMessage = () => {
  const handleSend = async (targetMemberId: number) => {
    const response = await SendMessageApi(targetMemberId);
    console.log(response);
  };

  return { handleSend };
};
