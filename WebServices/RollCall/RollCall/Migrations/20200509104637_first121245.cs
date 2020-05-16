using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RollCall.Migrations
{
    public partial class first121245 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_LeaveBalance_User_UserId",
                table: "LeaveBalance");

            migrationBuilder.DropIndex(
                name: "IX_LeaveBalance_UserId",
                table: "LeaveBalance");

            migrationBuilder.DropColumn(
                name: "Annula",
                table: "LeaveBalance");

            migrationBuilder.DropColumn(
                name: "UserId",
                table: "LeaveBalance");

            migrationBuilder.AddColumn<int>(
                name: "LeaveBalanceId",
                table: "User",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "Annual",
                table: "LeaveBalance",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<int>(
                name: "AttachmentFileId",
                table: "Leave",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "NumberOfDays",
                table: "Leave",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "Email",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    To = table.Column<string>(nullable: true),
                    Subject = table.Column<string>(nullable: true),
                    body = table.Column<string>(nullable: true),
                    sentTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Email", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "File",
                columns: table => new
                {
                    FileId = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    FileName = table.Column<string>(maxLength: 255, nullable: true),
                    ContentType = table.Column<string>(maxLength: 100, nullable: true),
                    Content = table.Column<byte[]>(nullable: true),
                    FileType = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_File", x => x.FileId);
                });

            migrationBuilder.CreateTable(
                name: "ForgotPassword",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Username = table.Column<string>(nullable: true),
                    eMail = table.Column<string>(nullable: true),
                    NationalId = table.Column<string>(nullable: true),
                    Otp = table.Column<string>(nullable: true),
                    NewPassword = table.Column<string>(nullable: true),
                    ConfirmPassword = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ForgotPassword", x => x.Id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_User_LeaveBalanceId",
                table: "User",
                column: "LeaveBalanceId");

            migrationBuilder.CreateIndex(
                name: "IX_Leave_AttachmentFileId",
                table: "Leave",
                column: "AttachmentFileId");

            migrationBuilder.AddForeignKey(
                name: "FK_Leave_File_AttachmentFileId",
                table: "Leave",
                column: "AttachmentFileId",
                principalTable: "File",
                principalColumn: "FileId",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_User_LeaveBalance_LeaveBalanceId",
                table: "User",
                column: "LeaveBalanceId",
                principalTable: "LeaveBalance",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Leave_File_AttachmentFileId",
                table: "Leave");

            migrationBuilder.DropForeignKey(
                name: "FK_User_LeaveBalance_LeaveBalanceId",
                table: "User");

            migrationBuilder.DropTable(
                name: "Email");

            migrationBuilder.DropTable(
                name: "File");

            migrationBuilder.DropTable(
                name: "ForgotPassword");

            migrationBuilder.DropIndex(
                name: "IX_User_LeaveBalanceId",
                table: "User");

            migrationBuilder.DropIndex(
                name: "IX_Leave_AttachmentFileId",
                table: "Leave");

            migrationBuilder.DropColumn(
                name: "LeaveBalanceId",
                table: "User");

            migrationBuilder.DropColumn(
                name: "Annual",
                table: "LeaveBalance");

            migrationBuilder.DropColumn(
                name: "AttachmentFileId",
                table: "Leave");

            migrationBuilder.DropColumn(
                name: "NumberOfDays",
                table: "Leave");

            migrationBuilder.AddColumn<decimal>(
                name: "Annula",
                table: "LeaveBalance",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<int>(
                name: "UserId",
                table: "LeaveBalance",
                type: "int",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_LeaveBalance_UserId",
                table: "LeaveBalance",
                column: "UserId");

            migrationBuilder.AddForeignKey(
                name: "FK_LeaveBalance_User_UserId",
                table: "LeaveBalance",
                column: "UserId",
                principalTable: "User",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
