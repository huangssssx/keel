/**
 * 格式化日期为 YYYY-MM-DD HH:mm:ss 格式
 * @param date 日期字符串或Date对象
 * @returns 格式化后的日期字符串
 */
export function formatDate(date: string | Date): string {
  const d = new Date(date);

  if (isNaN(d.getTime())) {
    return "-";
  }

  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, "0");
  const day = String(d.getDate()).padStart(2, "0");
  const hours = String(d.getHours()).padStart(2, "0");
  const minutes = String(d.getMinutes()).padStart(2, "0");
  const seconds = String(d.getSeconds()).padStart(2, "0");

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

/**
 * 格式化日期为 YYYY-MM-DD 格式
 * @param date 日期字符串或Date对象
 * @returns 格式化后的日期字符串
 */
export function formatDateShort(date: string | Date): string {
  const d = new Date(date);

  if (isNaN(d.getTime())) {
    return "-";
  }

  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, "0");
  const day = String(d.getDate()).padStart(2, "0");

  return `${year}-${month}-${day}`;
}

/**
 * 格式化相对时间（如：刚刚、x分钟前、x小时前、x天前）
 * @param date 日期字符串或Date对象
 * @returns 相对时间字符串
 */
export function formatRelativeTime(date: string | Date): string {
  const d = new Date(date);

  if (isNaN(d.getTime())) {
    return "-";
  }

  const now = new Date();
  const diff = now.getTime() - d.getTime();
  const minutes = Math.floor(diff / (1000 * 60));
  const hours = Math.floor(diff / (1000 * 60 * 60));
  const days = Math.floor(diff / (1000 * 60 * 60 * 24));

  if (minutes < 1) {
    return "刚刚";
  } else if (minutes < 60) {
    return `${minutes}分钟前`;
  } else if (hours < 24) {
    return `${hours}小时前`;
  } else if (days < 30) {
    return `${days}天前`;
  } else {
    return formatDateShort(date);
  }
}
