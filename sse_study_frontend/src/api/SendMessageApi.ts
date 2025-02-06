import { axiosInstance } from "../utils/apiConfig.tsx";

export const SendMessageApi = async (targetMemberId: number): Promise<string | undefined> => {
    try {
        const response = await axiosInstance.post(
            `${process.env.REACT_APP_API_BASE_URL}/send?targetMemberId=${targetMemberId}`
        );
        return response.data;
    } catch (error) {
        console.error("전송 실패:", error);
        return undefined;
    }
}