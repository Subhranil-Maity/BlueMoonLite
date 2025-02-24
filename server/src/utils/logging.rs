use colored::Colorize;

pub fn logg(
    level: LogLevel,
    message: &str,
) {
    let time = chrono::Local::now();
    let date = time.format("%Y-%m-%d %H:%M:%S");
    let lvl = match level { 
        LogLevel::INFO => "INFO".green(),
        LogLevel::WARN => "WARN".yellow(),
        LogLevel::ERROR => "ERROR".red(),
        LogLevel::DEBUG => "DEBUG".blue(),
    };
    println!("{} {}: {}", date, lvl.bold(), message);
}

pub fn debug(
    message: &str,
) {
    logg(LogLevel::DEBUG, message);
}

pub fn info(
    message: &str,
) {
    logg(LogLevel::INFO, message);
}

pub fn warn(
    message: &str,
) {
    logg(LogLevel::WARN, message);
}

pub fn error(
    message: &str,
) {
    logg(LogLevel::ERROR, message);
}




#[derive(Debug)]
pub enum LogLevel {
    INFO,
    WARN,
    ERROR,
    DEBUG,
}